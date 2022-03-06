package com.example.logisticappswift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatLogActivity : AppCompatActivity() {

    private val adapter = GroupAdapter<ViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)
        val user = intent.getParcelableExtra<User>("USER_KEY")
        if (user != null) {
            supportActionBar?.title = user.username
        }

        listenForMessage()
        send_message_btn.setOnClickListener {
            sendMessage(user!!.username)
            message_txt.setText("")
        }
        chat_log_recyclerview.adapter = adapter
    }

    private fun listenForMessage(){
        val user = intent.getParcelableExtra<User>("USER_KEY")
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/${user_data.username}/${user?.username}")
        ref.addChildEventListener(object:ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage :: class.java)
                if(chatMessage != null){
                    if(chatMessage.from == user_data.username){
                        adapter.add(ChatToRow(chatMessage.text,user!!))
                    }
                    else{
                        adapter.add(ChatFromRow(chatMessage.text, user!!))
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun sendMessage(to: String){
        val text = message_txt.text.toString()
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/${user_data.username}/$to").push()
        val ref2 = FirebaseDatabase.getInstance().getReference("/user-messages/$to/${user_data.username}").push()
        val chatMessage = ChatMessage(text, user_data.username, to, ref.key!!.toString())
        ref.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d("console", "Message send")
            }
        ref2.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d("console", "Message send")
            }
    }

    class ChatMessage(val text:String, val from:String, val to:String, val id:String){
        constructor(): this("","","","")
    }

    class ChatFromRow(val text:String, val user:User): Item<ViewHolder>(){
        override fun bind(viewHolder: ViewHolder, position: Int) {
            Picasso.get().load(user.profile_img_url).into(viewHolder.itemView.chat_log_from_profile_img)
            viewHolder.itemView.chat_log_from_message_txt.text = text
        }

        override fun getLayout(): Int {
            return R.layout.chat_from_row
        }

    }

    class ChatToRow(val text:String, val user:User): Item<ViewHolder>(){
        override fun bind(viewHolder: ViewHolder, position: Int) {
            Picasso.get().load(user_data.profile_img_url).into(viewHolder.itemView.chat_log_to_profile_img)
            viewHolder.itemView.chat_log_to_message_txt.text = text
        }

        override fun getLayout(): Int {
            return R.layout.chat_to_row
        }

    }
}