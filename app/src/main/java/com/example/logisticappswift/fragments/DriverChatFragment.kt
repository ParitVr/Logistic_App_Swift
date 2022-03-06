package com.example.logisticappswift.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.logisticappswift.ChatLogActivity
import com.example.logisticappswift.R
import com.example.logisticappswift.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.protobuf.Value
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.chat_new_messages.view.*
import kotlinx.android.synthetic.main.fragment_driver_chat.*
import kotlinx.android.synthetic.main.fragment_driver_chat.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DriverChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DriverChatFragment : Fragment() {
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
        val view: View = inflater!!.inflate(R.layout.fragment_driver_chat, container, false)
        fetchUser(view)
        return view
    }

    private fun fetchUser(view:View){
        val ref = FirebaseDatabase.getInstance().getReference("/users/client")
        ref.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                snapshot.children.forEach {
                    val user = it.getValue(User::class.java)
                    if(user != null){
                        adapter.add(DriverNewMessage(user))
                    }
                }
                adapter.setOnItemClickListener { item, view ->
                    val intent = Intent(view.context, ChatLogActivity :: class.java)
                    val userItem = item as DriverNewMessage
                    intent.putExtra("USER_KEY", userItem.user )
                    startActivity(intent)
                }
                view.driver_chat_recyclerview.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
    class DriverNewMessage(val user: User): Item<ViewHolder>(){
        override fun bind(viewHolder: ViewHolder, position: Int) {
            Picasso.get().load(user.profile_img_url).into(viewHolder.itemView.chat_profile_img)
            viewHolder.itemView.chat_username.text = user.username
        }
        override fun getLayout(): Int {
            return R.layout.chat_new_messages
        }
    }
}