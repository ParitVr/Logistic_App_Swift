package com.example.logisticappswift.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.logisticappswift.R
import com.example.logisticappswift.objects.CreatedPost
import com.example.logisticappswift.user_data
import com.google.firebase.database.FirebaseDatabase
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
        view.confirm_create_job_btn.setOnClickListener {
            if(title_txt.text.toString() == "" || deliver_from_txt.text.toString() == ""||
                    deliver_to_txt.text.toString() == "" || price_offer_txt.text.toString() == "" ||
                    load_txt.text.toString() == ""){
                return@setOnClickListener
            }
            val post_id = UUID.randomUUID().toString()
            var create_post = CreatedPost(title = title_txt.text.toString(), deliver_from = deliver_from_txt.text.toString(),
            deliver_to = deliver_to_txt.text.toString(), profile_img = user_data.profile_img_url, price_offer = price_offer_txt.text.toString(),
            posted_by = user_data.username, post_id = post_id, description = job_detail_multi_txt.text.toString(), load = load_txt.text.toString())
            val ref = FirebaseDatabase.getInstance().getReference("posts/$post_id").setValue(create_post).addOnSuccessListener {
                Log.d("console", "Post created successfully")
            }
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateJobFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}