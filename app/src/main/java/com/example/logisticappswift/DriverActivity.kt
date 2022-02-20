package com.example.logisticappswift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_driver.*
import kotlinx.android.synthetic.main.activity_driver.view.*



class DriverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver)
        val recycler_view = findViewById<RecyclerView>(R.id.recycler_view_new_job)
        val job_list = findViewById<TextView>(R.id.job_name_txt)
        val adapter = GroupAdapter<ViewHolder>()
        profile_img.visibility = View.GONE
        job_list.visibility = View.GONE
        val ref = FirebaseDatabase.getInstance().getReference("users/client")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    Log.d("console", it.toString())
                    val user = it.getValue(User::class.java)
                    if(user != null){
                        adapter.add(JobList(user))
                        Log.d("console", "User not null ${user.username}")
                    }
                }
                recycler_view_new_job.adapter = adapter
                Log.d("console", adapter.toString() + " " + R.layout.activity_driver)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        val i: Int = recycler_view_new_job.childCount
        Log.d("console", i.toString())
    }
    }

class JobList(val user:User): Item<ViewHolder>(){
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.job_name_txt.text = user.username
        Picasso.get().load(user.profile_img_url).into(viewHolder.itemView.profile_img)
    }

    override fun getLayout(): Int {
        return R.layout.activity_driver
    }
}


