package com.example.logisticappswift.fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.contentValuesOf
import com.example.logisticappswift.R
import com.example.logisticappswift.objects.CreatedPost
import com.example.logisticappswift.user_data
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_create_job.*
import kotlinx.android.synthetic.main.fragment_create_job.view.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CreateJobFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var imageUri:Uri? = null
    private var getImageResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            if(it.data?.data != null){
                imageUri = it.data?.data!!
                Toast.makeText(context, "Image Selected $imageUri", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater!!.inflate(R.layout.fragment_create_job, container, false)

        view.select_image_btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            intent.type = "image/*"
            getImageResult.launch(intent)
        }

        view.confirm_create_job_btn.setOnClickListener {
            Log.d("console", "Post btn pressed")
            if(title_txt.text.toString() == "" || deliver_from_txt.text.toString() == ""||
                    deliver_to_txt.text.toString() == "" || price_offer_txt.text.toString() == "" ||
                    load_txt.text.toString() == ""){
                Toast.makeText(context, "Please select fill out everything", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(imageUri == null){
                return@setOnClickListener
                Toast.makeText(context, "Please select an image!", Toast.LENGTH_SHORT).show()
            }
            Log.d("console", "Post is valid")
            val post_id = UUID.randomUUID().toString()
            var create_post = CreatedPost(title = title_txt.text.toString(), deliver_from = deliver_from_txt.text.toString(),
            deliver_to = deliver_to_txt.text.toString(), profile_img = user_data.profile_img_url, price_offer = price_offer_txt.text.toString(),
            posted_by = user_data.username, post_id = post_id, description = job_detail_multi_txt.text.toString(), load = load_txt.text.toString(),
            status = "open")
            val ref = FirebaseDatabase.getInstance().getReference("posts/$post_id").setValue(create_post).addOnSuccessListener {
                Log.d("console", "Post created successfully")
            }
            val ref2 = FirebaseDatabase.getInstance().getReference("/user-posts/${user_data.username}/$post_id").setValue(create_post)
            val ref_storage = imageUri?.let { img ->
                FirebaseStorage.getInstance().getReference("/images/post_images/$post_id/$post_id.jpg").putFile(img)
            }
            Toast.makeText(context, "Post Created", Toast.LENGTH_SHORT).show()
            title_txt.setText("")
            job_detail_multi_txt.setText("")
            deliver_from_txt.setText("")
            deliver_to_txt.setText("")
            load_txt.setText("")
            price_offer_txt.setText("")
            imageUri = null
        }


        return view
    }
}