package com.example.socialmediaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_)
     var email = findViewById<EditText>(R.id.email2)
     var password = findViewById<EditText>(R.id.password2)
     var btn = findViewById<Button>(R.id.sign_in)

        btn.setOnClickListener {
            if(email.text.isEmpty()||password.text.isEmpty()){

            }
            else{
                var email = email.text.toString()
                var pass = password.text.toString()
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(applicationContext,"Sign In Success",Toast.LENGTH_SHORT).show()
                            // TODO:add intent2
                            var intent = Intent(this@Login_Activity,MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(applicationContext,"Sign In Failed",Toast.LENGTH_SHORT).show()

                        }
                    }
            }
        }

    }
}