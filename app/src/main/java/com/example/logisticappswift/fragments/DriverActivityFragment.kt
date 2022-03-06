package com.example.logisticappswift.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.logisticappswift.JobDetailActivity
import com.example.logisticappswift.R
import com.example.logisticappswift.objects.CreatedPost
import com.example.logisticappswift.objects.JobListClickListener
import com.example.logisticappswift.objects.post_list
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.driver_job_list.view.*
import kotlinx.android.synthetic.main.fragment_driver_activity.*
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
        //fetchPost()
        //test()
//        view.recyclerView.apply{
//            layoutManager = GridLayoutManager(context, 1)
//            adapter = DriverJobListCardAdapter(post_list, this@DriverActivityFragment)
//        }
        fetchPost(view)

        return view
    }


//    override fun onPostClick(post: CreatedPost) {
//        super.onPostClick(post)
//        Toast.makeText(context, "post by ${post.posted_by}", Toast.LENGTH_SHORT).show()
//        var intent = Intent(context, JobDetailActivity :: class.java)
//        intent.putExtra("title", post.title)
//        intent.putExtra("description", post.description)
//        intent.putExtra("deliver_from", post.deliver_from)
//        intent.putExtra("deliver_to", post.deliver_to)
//        intent.putExtra("load", post.load)
//        intent.putExtra("price_offer",post.price_offer)
//        intent.putExtra("id", post.post_id)
//        intent.putExtra("config", true)
//        startActivity(intent)
//    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }

    private fun fetchPost(view:View){
        val ref = FirebaseDatabase.getInstance().getReference("posts")
        ref.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                snapshot.children.forEach {
                    if(it.child("status").value.toString() == "open"){
                        Log.d("console", it.toString())
                        val post = it.getValue(CreatedPost::class.java)
                        if(post != null){
                            adapter.add(DriverNewPost(post))
                        }
                        adapter.setOnItemClickListener { item, view ->
                            val intent = Intent(view.context, JobDetailActivity :: class.java)
                            val postItem = item as DriverNewPost
                            intent.putExtra("title", postItem.post.title)
                            intent.putExtra("description", postItem.post.description)
                            intent.putExtra("deliver_from", postItem.post.deliver_from)
                            intent.putExtra("deliver_to", postItem.post.deliver_to)
                            intent.putExtra("load", postItem.post.load)
                            intent.putExtra("price_offer",postItem.post.price_offer)
                            intent.putExtra("id", postItem.post.post_id)
                            intent.putExtra("posted_by", postItem.post.posted_by)
                            intent.putExtra("config", true)
                            startActivity(intent)
                        }
                        view.driver_job_list_recyclerview.adapter = adapter
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
//    private fun test(){
//        var post = CreatedPost(
//            "Parit Vorasarannnnnnnn",
//            "Testing title",
//            "https://firebasestorage.googleapis.com/v0/b/logistic-app-bee2d.appspot.com/o/images%2Fprofile_images%2F8480fd66-f432-4e85-a5a3-dbfcc54134ae?alt=media&token=34bef3cb-a9de-4053-9d69-7f1ecca1fc42",
//            "My house",
//            "Your house",
//            "50000",
//            "Hello",
//            "5000",
//            "1asfe3g3r",
//            "open",
//        )
//        post_list.add(post)
//    }
    class DriverNewPost(val post:CreatedPost): Item<ViewHolder>(){
        override fun bind(viewHolder: ViewHolder, position: Int) {
            Picasso.get().load(post.profile_img).into(viewHolder.itemView.posted_by_profile_img)
            viewHolder.itemView.posted_by_txt.text = post.posted_by
            viewHolder.itemView.job_title_txt.text = post.title
            viewHolder.itemView.job_load_txt.text = post.load
        }

        override fun getLayout(): Int {
            return R.layout.driver_job_list
        }

    }

//    private fun fetchPost(){
//        var post:CreatedPost
//        val ref = FirebaseDatabase.getInstance().getReference("posts")
//        ref.addListenerForSingleValueEvent(object:ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                snapshot.children.forEach {
//                    if(it.child("status").value.toString() == "open"){
//                        Log.d("console", it.toString())
//                        //Toast.makeText(context, "${it.toString()}", Toast.LENGTH_SHORT).show()
//
//                        post = CreatedPost("","","","","","",
//                            "","","","")
//
//                        post.posted_by = it.child("posted_by").value.toString()
//                        post.deliver_from = it.child("deliver_from").value.toString()
//                        post.deliver_to = it.child("deliver_to").value.toString()
//                        post.title = it.child("title").value.toString()
//                        post.description = it.child("description").value.toString()
//                        post.load = it.child("load").value.toString()
//                        post.price_offer = it.child("price_offer").value.toString()
//                        post.post_id = it.child("post_id").value.toString()
//                        post.profile_img = it.child("profile_img").value.toString()
//                        post.status = it.child("status").value.toString()
//
//                        post_list.add(post)
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                return
//            }
//
//        })
//    }
}