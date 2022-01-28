package com.example.logisticappswift

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
            if(email_login_txt.text.toString() == "" || password_login_txt.text.toString() == ""){
                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email_login_txt.text.toString(),
                password_login_txt.text.toString()

            )
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        val intent = Intent(this, HomeActivity::class.java)

                        val ref = FirebaseDatabase.getInstance().getReference()
                        val query = ref.child("users/client/").orderByChild("email").equalTo(email_login_txt.text.toString())
                            .addValueEventListener(object : ValueEventListener{
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    Log.d("console", snapshot.key.toString())
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }

                            })
                        //val value = query.get().result
                        //val result = value.toString()
                        //Log.d("console", "$result")
                        //Log.d("console", "hello")
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