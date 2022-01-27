package com.example.logisticappswift

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class RegistrationActivity : AppCompatActivity() {

    //global variable
    lateinit var global_uri:Uri
    var status = "client"
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //Declaring variables
        val username_txt = findViewById<TextView>(R.id.username_txt);
        val email_txt = findViewById<TextView>(R.id.email_txt);
        val password_txt = findViewById<TextView>(R.id.password_txt);
        val register_btn = findViewById<Button>(R.id.register_btn);
        val to_login_txt = findViewById<TextView>(R.id.have_account_txt);
        val driver_chk = findViewById<CheckBox>(R.id.driver_chk);
        val client_chk = findViewById<CheckBox>(R.id.client_chk);
        val photo_img = findViewById<ImageView>(R.id.photo_img)
        //

        //on click functions
        register_btn.setOnClickListener {
            if(password_txt.text.length < 7){
                Toast.makeText(this,"Password must be 7 characters or more", Toast.LENGTH_SHORT).show();
                return@setOnClickListener;
            }
            Log.d("console", "Email is ${email_txt.text}")
            Log.d("console", "password is ${password_txt.text}")
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email_txt.text.toString(), password_txt.text.toString())
                .addOnCompleteListener {
                    if (!it.isSuccessful) return@addOnCompleteListener
                    Log.d("console", "Account successfully created with uid : ${it.result?.user?.uid}");
                    upload_image_to_firebase();
                }
            val intent = Intent(this@RegistrationActivity, LoginActivity :: class.java );
            intent.putExtra("email",email_txt.text.toString())
            intent.putExtra("password",password_txt.text.toString())
            intent.putExtra("check", true);
            startActivity(intent);
        }
        photo_img.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            intent.type = "image/*"
            getResult.launch(intent)
        }
        driver_chk.setOnClickListener {
            client_chk.isChecked = false;
            status = "driver"
        }
        client_chk.setOnClickListener {
            driver_chk.isChecked = false;
            status = "client"
        }
        to_login_txt.setOnClickListener {
            val intent = Intent(this@RegistrationActivity, LoginActivity :: class.java );
            startActivity(intent);
        }
        //

    }
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){
                val upload_photo_txt = findViewById<TextView>(R.id.upload_photo_txt)
                //val value = it.data?.getStringExtra("input")
                val photo_img = findViewById<ImageView>(R.id.photo_img)
                val uri = it.data?.data
                photo_img.setImageURI(uri)
                upload_photo_txt.text = ""
                if (uri != null) {
                    global_uri = uri
                }
                Log.d("console", "Img uploaded $uri ${it.data}")
            }
        }
    private fun upload_image_to_firebase(){
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(global_uri)
            .addOnSuccessListener {
                Log.d("console", "Success $global_uri")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("console", "File location: $it")
                    save_user_to_firebase(it.toString())
                }
            }
    }
    fun save_user_to_firebase(profile_img_url:String){
        Log.d("console", "Adding User to database...")
        val username_txt = findViewById<TextView>(R.id.username_txt);
        val email_txt = findViewById<TextView>(R.id.email_txt);

        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("users/$status/$uid")

        val user =  User(uid, username_txt.text.toString(), profile_img_url, status, email_txt.text.toString())
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("console", "User registered successfully")
            }
    }
}
class User(val uid:String, val username:String, val profile_img_url:String, val status:String, val email:String)