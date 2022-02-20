package com.example.logisticappswift

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_client.*

class ClientActivity : AppCompatActivity() {

    var tab_title = arrayOf("Home", "Create Job", "Setting")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)


        //init
        val user_status_txt = findViewById<TextView>(R.id.display_status_txt)
        user_status_txt.text = user_data.status
        //
    }
}