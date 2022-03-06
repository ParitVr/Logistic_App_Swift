package com.example.logisticappswift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.logisticappswift.fragments.DriverActivityFragment
import com.example.logisticappswift.fragments.DriverChatFragment
import com.example.logisticappswift.fragments.DriverHomeFragment
import com.example.logisticappswift.fragments.SettingFragment
//import com.example.logisticappswift.objects.post_list
import kotlinx.android.synthetic.main.activity_driver.*


class DriverActivity : AppCompatActivity() {

    private val homeFragment = DriverHomeFragment()
    private val jobListFragment = DriverActivityFragment()
    private val chatFragment = DriverChatFragment()
    private val settingFragment = SettingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver)

        replace_fragment(homeFragment)

        driver_bottom_nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.driver_home_nav -> replace_fragment(homeFragment)
                R.id.driver_post_nav -> replace_fragment(jobListFragment)
                R.id.driver_chat_nav -> replace_fragment(chatFragment)
                R.id.driver_setting_nav -> replace_fragment(settingFragment)
            }
            true
        }

    }

    private fun replace_fragment(fragment: Fragment){
        if(fragment != null){
            //post_list.clear()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_driver, fragment)
            transaction.commit()
        }
    }
}


