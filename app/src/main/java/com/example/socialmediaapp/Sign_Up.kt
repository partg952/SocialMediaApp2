package com.example.socialmediaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Sign_Up : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    var email = findViewById<EditText>(R.id.email)
    var password = findViewById<EditText>(R.id.password)
    var btn = findViewById<Button>(R.id.sign_up)
    var text = findViewById<TextView>(R.id.textView)
    
    text.setOnClickListener {
    var inent = Intent(this@Sign_Up,Login_Activity::class.java)
      startActivity(inent)
    }

    btn.setOnClickListener {
      if(email.text.isEmpty()||password.text.isEmpty()){
        return@setOnClickListener
      }
      else{
        var pass = password.text.toString()
        var email = email.text.toString()
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,pass)
          .addOnCompleteListener{
            if(it.isSuccessful){
              var gmail = FirebaseAuth.getInstance().currentUser?.email?.removeSuffix("@gmail.com").toString()
              Toast.makeText(applicationContext,"Sign Up Success",Toast.LENGTH_SHORT).show()
              FirebaseDatabase.getInstance().getReference().child("user").child("$gmail").child("email")
                .setValue(email)
              FirebaseDatabase.getInstance().getReference().child("user").child("$gmail").child("LikeSystem").child("Likes")
                      .setValue(0)
              // TODO:Add Intent
              var intent = Intent(this@Sign_Up,MainActivity::class.java)
              intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
              startActivity(intent)
            }
            else{
              Toast.makeText(applicationContext,"Sign Up Failed",Toast.LENGTH_SHORT).show()
            }
          }
      }
    }
  }
}