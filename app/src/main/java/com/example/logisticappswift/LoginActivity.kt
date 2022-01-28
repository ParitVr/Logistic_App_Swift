package com.example.logisticappswift

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Declare variable
        //from intent
        var check = intent.getBooleanExtra("check", false);
        val email = intent.getStringExtra("email");
        val password = intent.getStringExtra("password")
        //local
        val email_login_txt = findViewById<TextView>(R.id.email_login_txt);
        val password_login_txt = findViewById<TextView>(R.id.password_login_txt);
        val dont_have_account_txt = findViewById<TextView>(R.id.dont_have_account_txt)
        val login_btn = findViewById<Button>(R.id.login_btn)
        //
        //init
        if (check == true) {
            email_login_txt.text = email.toString();
            password_login_txt.text = password.toString();
        }
        //
        //on click functions
        login_btn.setOnClickListener {

            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email_login_txt.text.toString(),
                password_login_txt.text.toString()
            )
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }

                }
            dont_have_account_txt.setOnClickListener {
                val intent = Intent(this, RegistrationActivity::class.java)
                startActivity(intent)
            }
        }
    }
}