package com.example.logisticappswift.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.logisticappswift.JobDetailActivity
import com.example.logisticappswift.R
import com.example.logisticappswift.User
import com.example.logisticappswift.objects.CreatedPost
import com.example.logisticappswift.user_data
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.client_home_post.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.lang.System.load
import java.util.ServiceLoader.load

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        //Toast.makeText(context, "View created", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        fetchPost(view)
        Picasso.get().load(user_data.profile_img_url).into(view.client_home_profile_img)
        view.client_home_username.text = user_data.username
        return view
    }

    private fun fetchPost(view:View){
        val ref = FirebaseDatabase.getInstance().getReference("/user-posts/${user_data.username}")
        ref.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                snapshot.children.forEach {
                    Log.d("console", "$snapshot")
                    val post = it.getValue(CreatedPost::class.java)
                    if(post != null){
                        adapter.add(ClientPost(post))
                    }
                    adapter.setOnItemClickListener{
                            item, view ->
                        val intent = Intent(view.context, JobDetailActivity::class.java)
                        val postItem = item as ClientPost
                        intent.putExtra("title", postItem.post.title)
                        intent.putExtra("description", postItem.post.description)
                        intent.putExtra("deliver_from", postItem.post.deliver_from)
                        intent.putExtra("deliver_to", postItem.post.deliver_to)
                        intent.putExtra("load", postItem.post.load)
                        intent.putExtra("price_offer",postItem.post.price_offer)
                        intent.putExtra("id", postItem.post.post_id)
                        intent.putExtra("posted_by", postItem.post.posted_by)
                        intent.putExtra("profile_img", postItem.post.profile_img)
                        intent.putExtra("config", false)
                        intent.putExtra("config2", true)
                        startActivity(intent)
                    }
                }
                client_home_recyclerview.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    class ClientPost(val post:CreatedPost):Item<ViewHolder>(){
        override fun bind(viewHolder: ViewHolder, position: Int) {
            Picasso.get().load(post.profile_img).into(viewHolder.itemView.client_home_post_img)
            viewHolder.itemView.client_home_post_title.text = post.title
            viewHolder.itemView.client_home_post_status.text = post.status
        }

        override fun getLayout(): Int {
            return R.layout.client_home_post
        }

    }
}