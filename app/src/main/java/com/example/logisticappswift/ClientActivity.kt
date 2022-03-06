package com.example.logisticappswift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.logisticappswift.fragments.ClientChatFragment
import com.example.logisticappswift.fragments.CreateJobFragment
import com.example.logisticappswift.fragments.HomeFragment
import com.example.logisticappswift.fragments.SettingFragment
import kotlinx.android.synthetic.main.activity_client.*

class ClientActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val createPostFragment = CreateJobFragment()
    private val settingFragment = SettingFragment()
    private val chatFragment = ClientChatFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        replace_fragment(homeFragment)

        client_bottom_nav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.client_home_nav -> replace_fragment(homeFragment)
                R.id.client_post_nav -> replace_fragment(createPostFragment)
                R.id.client_chat_nav -> replace_fragment(chatFragment)
                R.id.client_setting_nav -> replace_fragment(settingFragment)
            }
            true
        }
    }

    private fun replace_fragment(fragment:Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container_client, fragment)
            transaction.commit()
            Log.d("console", "${fragment.toString()}")
        }
    }
}