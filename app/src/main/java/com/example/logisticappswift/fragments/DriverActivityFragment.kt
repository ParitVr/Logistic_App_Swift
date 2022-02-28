package com.example.logisticappswift.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.logisticappswift.JobDetailActivity
import com.example.logisticappswift.R
import com.example.logisticappswift.adapters.DriverJobListCardAdapter
import com.example.logisticappswift.databinding.DriverJobListBinding
import com.example.logisticappswift.databinding.FragmentDriverActivityBinding
import com.example.logisticappswift.objects.CreatedPost
import com.example.logisticappswift.objects.JobListClickListener
import com.example.logisticappswift.objects.post_list
import com.example.logisticappswift.user_data
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_driver_activity.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DriverActivityFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DriverActivityFragment : Fragment(), JobListClickListener {
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
        val view: View = inflater!!.inflate(R.layout.fragment_driver_activity, container, false)
        fetchPost()
        test()
        view.recyclerView.apply{
            layoutManager = GridLayoutManager(context, 1)
            adapter = DriverJobListCardAdapter(post_list, this@DriverActivityFragment)
        }
        return view
    }

    override fun onPostClick(post: CreatedPost) {
        super.onPostClick(post)
        Toast.makeText(context, "post by ${post.posted_by}", Toast.LENGTH_SHORT).show()
        var intent = Intent(context, JobDetailActivity :: class.java)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun test(){
        var post =CreatedPost(
            "Parit Vorasarannnnnnnn",
            "Testing title",
            user_data.profile_img_url,
            "My house",
            "Your house",
            "50000",
            "Hello",
            "5000",
            "1asfe3g3r",
            "open",
        )
        post_list.add(post)
    }

    private fun fetchPost(){
        var post:CreatedPost
        val ref = FirebaseDatabase.getInstance().getReference("posts")
        ref.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    if(it.child("status").value.toString() == "open"){
                        Log.d("console", it.toString())
                        //Toast.makeText(context, "${it.toString()}", Toast.LENGTH_SHORT).show()

                        post = CreatedPost("","","","","","",
                            "","","","")

                        post.posted_by = it.child("posted_by").value.toString()
                        post.deliver_from = it.child("deliver_from").value.toString()
                        post.deliver_to = it.child("deliver_to").value.toString()
                        post.title = it.child("title").value.toString()
                        post.description = it.child("description").value.toString()
                        post.load = it.child("load").value.toString()
                        post.price_offer = it.child("price_offer").value.toString()
                        post.post_id = it.child("post_id").value.toString()
                        post.profile_img = it.child("profile_img").value.toString()
                        post.status = it.child("status").value.toString()

                        post_list.add(post)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                return
            }

        })

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DriverActivityFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DriverActivityFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}