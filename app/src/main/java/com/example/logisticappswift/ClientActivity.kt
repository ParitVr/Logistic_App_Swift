package com.example.logisticappswift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ClientActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)


        //init
        val user_status_txt = findViewById<TextView>(R.id.display_status_txt)
        user_status_txt.text = user_data.status
        //
    }
}