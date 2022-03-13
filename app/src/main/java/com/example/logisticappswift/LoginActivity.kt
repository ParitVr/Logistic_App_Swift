package com.example.logisticappswift

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.logisticappswift.objects.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Declare variable
        //from intent
        var check = intent.getBooleanExtra("check", false);
        val email = intent.getStringExtra("email");
        val password = intent.getStringExtra("password")
        val username = intent.getStringExtra("username")
        var loading = intent.getBooleanExtra("loading", false)
        //local
        val email_login_txt = findViewById<TextView>(R.id.email_login_txt);
        val password_login_txt = findViewById<TextView>(R.id.password_login_txt);
        val dont_have_account_txt = findViewById<TextView>(R.id.dont_have_account_txt)
        val login_btn = findViewById<Button>(R.id.login_btn)
        //
        //init
        if (check == true) {
            email_login_txt.text = username.toString();
            password_login_txt.text = password.toString();
        }
        if(loading){
            val loading = LoadingDialog(this)
            loading.startLoading()
//            val handler = Handler()
//            handler.postDelayed(object:Runnable{
//                override fun run() {
//                    loading.isDismiss()
//                }
//            }, 1000)
            Handler(Looper.getMainLooper()).postDelayed({
                loading.isDismiss()
            }, 3000)
        }
        //
        //on click functions
        login_btn.setOnClickListener {
            if(email_login_txt.text.toString() == "" || password_login_txt.text.toString() == ""){
                Toast.makeText(this, "Invalid login", Toast.LENGTH_SHORT).show()
                Log.d("console", "Login failed email = ${email_login_txt.text.toString()}\nPassword = ${password_login_txt.text.toString()}")
                return@setOnClickListener
            }
            val check_acc = FirebaseDatabase.getInstance().getReference("users/client/")
            check_acc.child(email_login_txt.text.toString()).get().addOnSuccessListener {
                if(it.exists()){
                    val email_key = it.child("email").value
                    Log.d("console", email_key.toString())
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        email_key.toString(),
                        password_login_txt.text.toString()

                    )
                        .addOnCompleteListener(this) { task ->

                            if (task.isSuccessful) {
                                Log.d("console", email_key.toString())
                                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                                var ref = FirebaseDatabase.getInstance().getReference("users/client/${email_login_txt.text.toString()}")
                                ref.get().addOnSuccessListener {
                                    if(it.exists()){
                                         ref.child("profile_img_url").get().addOnSuccessListener {
                                             user_data.profile_img_url = it.value.toString()
                                         }
                                         ref.child("uid").get().addOnSuccessListener {
                                             user_data.uid = it.value.toString()
                                        }
                                        ref.child("email").get().addOnSuccessListener{
                                            user_data.email = it.value.toString()
                                        }
                                        user_data.username = email_login_txt.text.toString()
                                        user_data.status = "client"
                                        Log.d("console", "${user_data.uid}\n${user_data.profile_img_url}" )
                                    }
                                    else{
                                        user_data.username = email_login_txt.text.toString()
                                        user_data.status = "driver"
                                    }
                                }
                                if(user_data.status == "client"){
                                    Log.d("console", "Logged in as client")
                                    val intent = Intent(this, ClientActivity::class.java)
                                    startActivity(intent)
                                }
                                else{
                                    return@addOnCompleteListener
                                    Log.d("console", "Logged in as driver1")
                                    val intent = Intent(this, DriverActivity::class.java)
                                    startActivity(intent)
                                }
                                return@addOnCompleteListener
                            } else {
                                Toast.makeText(this, "Invalid Login 2", Toast.LENGTH_SHORT).show()
                                return@addOnCompleteListener
                            }

                        }
                }
                else{
                    val check_acc = FirebaseDatabase.getInstance().getReference("users/driver/")
                    check_acc.child(email_login_txt.text.toString()).get().addOnSuccessListener {
                        if(it.exists()){
                            val email_key = it.child("email").value
                            Log.d("console", email_key.toString())
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(
                                email_key.toString(),
                                password_login_txt.text.toString()

                            )
                                .addOnCompleteListener(this) { task ->
                                    if(task.isSuccessful){
                                        var ref = FirebaseDatabase.getInstance().getReference("users/driver/${email_login_txt.text.toString()}")
                                        //Log.d("console", email_key.toString())
                                        Log.d("console", "${user_data.uid}\n${user_data.profile_img_url}" )
                                        ref.child("profile_img_url").get().addOnSuccessListener {
                                            user_data.profile_img_url = it.value.toString()
                                        }
                                        ref.child("uid").get().addOnSuccessListener {
                                            user_data.uid = it.value.toString()
                                        }
                                        ref.child("email").get().addOnSuccessListener{
                                            user_data.email = it.value.toString()
                                        }
                                        user_data.username = email_login_txt.text.toString()
                                        user_data.status = "driver"
                                        Log.d("console", "Logged in as driver2")
                                        val intent = Intent(this, DriverActivity::class.java)
                                        startActivity(intent)

                                    }
                                }
                        }
                        else{
                            Toast.makeText(this, "Invalid Login", Toast.LENGTH_SHORT).show()
                            return@addOnSuccessListener
                        }
                    }
                }
            }
        }
        dont_have_account_txt.setOnClickListener {
            Log.d("console", "go to register")
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}