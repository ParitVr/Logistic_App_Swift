package com.example.logisticappswift

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.logisticappswift.fragments.DriverChatFragment
import com.example.logisticappswift.objects.CreatedPost
import com.example.logisticappswift.user_data.Companion.uid
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_job_detail.*
import java.io.File

class JobDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_detail)

        /////init
        var title = intent.getStringExtra("title")
        var description:String? = intent.getStringExtra("description")
        var deliver_from = intent.getStringExtra("deliver_from")
        var deliver_to = intent.getStringExtra("deliver_to")
        var load = intent.getStringExtra("load")
        var price_offer = intent.getStringExtra("price_offer")
        var id = intent.getStringExtra("id")
        var posted_by = intent.getStringExtra("posted_by")
        var profile_img = intent.getStringExtra("profile_img")
        var config = intent.getBooleanExtra("config", true)
        var config2 = intent.getBooleanExtra("config2", false)

        //set image
        var ref = FirebaseStorage.getInstance().reference.child("images/post_images/$id/$id.jpg")
        var local_file = File.createTempFile("temp_image", "jpg")
        ref.getFile(local_file).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(local_file.absolutePath)
            detail_job_img.setImageBitmap(bitmap)
        }
        //
        if(description != null){
            detail_description_txt.setText(description)
            detail_description_txt.setOnKeyListener(null)
        }
        detail_title_txt.text = title
        //detail_description_txt.setText(description)
        //detail_description_txt.setOnKeyListener(null)
        detail_deliver_from_txt.text = deliver_from
        detail_deliver_to_txt.text = deliver_to
        detail_load_txt.text = load
        detail_price_offer_txt.text = price_offer

        if(!config){
            accept_job_btn.visibility = View.GONE
            job_detail_complete_btn.visibility = View.VISIBLE
        }
        if(config){
            accept_job_btn.visibility = View.VISIBLE
            job_detail_complete_btn.visibility = View.GONE
        }
        if(config2){
            accept_job_btn.visibility = View.GONE
            job_detail_complete_btn.visibility = View.GONE
        }
        /////
        accept_job_btn.setOnClickListener {

            //get the post owner's reference
            val posted_by_detail_ref = FirebaseDatabase.getInstance().getReference("/users/client")
            posted_by_detail_ref.child("${posted_by}").get().addOnSuccessListener {

                //create node on driver's job
                val accept_job = CreatedPost(posted_by.toString(), title.toString(), profile_img.toString(), deliver_from.toString(),
                    deliver_to.toString(), price_offer.toString(), description.toString(), load.toString(), id.toString(), "pending")
                val create_node = FirebaseDatabase.getInstance().getReference("/accepted_job/${user_data.username}/$id")
                create_node.setValue(accept_job)
                //

                //sending auto message to post owner *need all the user's information
                val auto_message_ref = FirebaseDatabase.getInstance().getReference("/user-messages/${user_data.username}/$posted_by").push()
                val auto_message_ref2 = FirebaseDatabase.getInstance().getReference("/user-messages/$posted_by/${user_data.username}").push()
                val username = it.child("username").value.toString()
                val uid = it.child("uid").value.toString()
                val profile_img = it.child("profile_img_url").value.toString()
                val status = it.child("status").value.toString()
                val email = it.child("email").value.toString()
                val userData = User(uid,username,profile_img,status,email)
                val text = "Hi, I am on my way to pick up the items.\n(This is an auto generated message)"
                val chatMessage = ChatLogActivity.ChatMessage(text, user_data.username, posted_by.toString(), auto_message_ref.key!!.toString())

                //adding message to database
                auto_message_ref.setValue(chatMessage)
                auto_message_ref2.setValue(chatMessage)

                //change status of post to pending
                val change_status_ref = FirebaseDatabase.getInstance().getReference("/posts/$id")
                change_status_ref.child("status").setValue("pending")
                val intent = Intent(this, ChatLogActivity :: class.java)
                var userItem = userData as User
                intent.putExtra("USER_KEY", userItem)
                startActivity(intent)
            }
        }

        job_detail_complete_btn.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("accepted_job/${user_data.username}/$id")
            ref.child("status").setValue("Complete")
            val ref2 = FirebaseDatabase.getInstance().getReference("user-posts/$posted_by/$id")
            ref2.child("status").setValue("Complete")
        }

        detail_deliver_from_txt.setOnClickListener {
            var locationFrom = detail_deliver_from_txt.text.toString()
            val gmmIntentUri =
                Uri.parse("geo:0,0?q=$locationFrom")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        detail_deliver_to_txt.setOnClickListener {

            var locationTo = detail_deliver_to_txt.text.toString()
            val gmmIntentUri =
                Uri.parse("geo:0,0?q=$locationTo")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
            //Commit

        }
    }
}