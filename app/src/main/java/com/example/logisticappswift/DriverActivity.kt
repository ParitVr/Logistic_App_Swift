package com.example.logisticappswift

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DriverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver)

        val user_status_txt = findViewById<TextView>(R.id.display_status_driver_txt)

        user_status_txt.text = user_data.status
    }
}