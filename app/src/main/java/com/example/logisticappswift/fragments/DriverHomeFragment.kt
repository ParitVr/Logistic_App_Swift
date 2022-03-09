package com.example.logisticappswift.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.logisticappswift.JobDetailActivity
import com.example.logisticappswift.R
import com.example.logisticappswift.adapters.DriverPageAdapter
import com.example.logisticappswift.objects.CreatedPost
import com.example.logisticappswift.user_data
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_driver.*
import kotlinx.android.synthetic.main.driver_home_job.view.*
import kotlinx.android.synthetic.main.fragment_driver_home.*
import kotlinx.android.synthetic.main.fragment_driver_home.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DriverHomeFragment : Fragment() {

    private val tabTitle = arrayOf("My Job", "History")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //replace_fragment(jobAccepted)
//
//        driver_homepage_nav.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.driver_job_accepted_nav -> replace_fragment(jobAccepted)
//                R.id.driver_job_history_nav ->  replace_fragment(jobAccepted)
//            }
//            true
//        }
        val view = inflater.inflate(R.layout.fragment_driver_home, container, false)
//        view.driver_home_viewpager.adapter = DriverPageAdapter(parentFragmentManager, lifecycle)
//        TabLayoutMediator(view.driver_home_tablayout, view.driver_home_viewpager){
//            tab, position ->
//            tab.text = tabTitle[position]
//        }.attach()
        fetchJob(view)
        Picasso.get().load(user_data.profile_img_url).into(view.driver_home_profile_img)
        view.driver_home_username.text = user_data.username
        return view
    }

    private fun fetchJob(view:View){
        val ref = FirebaseDatabase.getInstance().getReference("/accepted_job/${user_data.username}")
        ref.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val adapter = GroupAdapter<ViewHolder>()
                snapshot.children.forEach {
                    Log.d("console", "${it.child("status")}")
                    if(it.child("status").value.toString() == "pending"){
                        val job = it.getValue(CreatedPost :: class.java)
                        if(job != null){
                            adapter.add(DriverJob(job))
                        }
                        adapter.setOnItemClickListener{
                            item, view ->
                            val intent = Intent(view.context, JobDetailActivity::class.java)
                            val postItem = item as DriverJob
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
                            startActivity(intent)
                        }
                    }
                }
                driver_home_recyclerview.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }
    private class DriverJob(val post:CreatedPost): Item<ViewHolder>(){
        override fun bind(viewHolder: ViewHolder, position: Int) {
            viewHolder.itemView.driver_home_job_title.text = post.title
            Picasso.get().load(post.profile_img).into(viewHolder.itemView.driver_home_job_img)
        }

        override fun getLayout(): Int {
            return R.layout.driver_home_job
        }

    }

//    private fun replace_fragment(fragment: Fragment){
//        if(fragment != null){
//            //post_list.clear()
//            val transaction = parentFragmentManager.beginTransaction()
//            transaction.replace(R.id.driver_home_fragment_container, fragment)
//            transaction.commit()
//        }
//    }
}