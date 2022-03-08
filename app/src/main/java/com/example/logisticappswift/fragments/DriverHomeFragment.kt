package com.example.logisticappswift.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.logisticappswift.R
import com.example.logisticappswift.user_data
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_driver.*
import kotlinx.android.synthetic.main.fragment_driver_home.*
import kotlinx.android.synthetic.main.fragment_driver_home.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class DriverHomeFragment : Fragment() {

    private val jobAccepted =DriverHomeAcceptedJobFragment()
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
        Picasso.get().load(user_data.profile_img_url).into(view.driver_home_profile_img)
        view.driver_home_username.text = user_data.username
        return view
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