package com.example.socialmediaapp

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.lang.StringBuilder
import java.security.Permission
import java.util.*
import java.util.jar.Manifest

private lateinit var camera:ImageButton
private lateinit var folder:ImageButton
private lateinit var image:ImageView
private lateinit var upload:ImageButton
class ImageAdd : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_add)
        var array = arrayOf(android.Manifest.permission.CAMERA)
        folder = findViewById(R.id.imageButton)
        image = findViewById(R.id.imageView)
      
      
      

        upload = findViewById(R.id.imageButton3)

        var number = 0
        var number2=0

//        requestPermissions(array,50)


        var getdata = object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()
                for(i in snapshot.children){
                    var num = i.child("num").getValue().toString()
                    if(num=="null"){

                    }
                    else{
                        number = num.toInt()
                        sb.append(num)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }

        FirebaseDatabase.getInstance().getReference().addValueEventListener(getdata)

        var getdata2 = object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var sb = StringBuilder()
                for(i in snapshot.children){
                    var num = i.child("uploads").getValue().toString()
                    if(num=="null"){

                    }
                    else{
                        number2 = num.toInt()
                        Log.d("Num2","$number2")
                        sb.append(num)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }

        FirebaseDatabase.getInstance().getReference().child("user").child("${FirebaseAuth.getInstance().currentUser?.email?.removeSuffix("@gmail.com")}").addValueEventListener(getdata2)


        folder.setOnClickListener {
            var intent2 = Intent(Intent.ACTION_PICK)
            intent2.type = "image/*"
            startActivityForResult(intent2,49)
        }

        upload.setOnClickListener {
            upload(number,number2)
        }
      
    }

    private var uri: Uri?=null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        
         if(requestCode==49&&resultCode==Activity.RESULT_OK&&data!=null){
            var data2 = data.data
            image.setImageURI(data2)
            uri = data2
        }
     
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun upload(num:Int,num2:Int) {
        var num = num+1
        var num2 = num2+1

        var filename = UUID.randomUUID().toString()

        var ref =  FirebaseStorage.getInstance().getReference().child("images").child("$filename")

        var progress = ProgressDialog(this)
        
        if(uri!=null){
          FirebaseStorage.getInstance().getReference().child("images").child("$filename")
                  .putFile(uri!!).addOnProgressListener {
                    progress.setTitle("Uploading Picture...")
                    progress.setCancelable(false)
                    progress.show()
                  }.addOnSuccessListener {
                    ref.downloadUrl.addOnCompleteListener {
                      if(it.isSuccessful){
                        progress.hide()
                        var url = it.result
                        FirebaseDatabase.getInstance().getReference().child("user").child("${FirebaseAuth.getInstance().currentUser?.email?.removeSuffix("@gmail.com")}").child("uploaded").child("$num2").setValue("$url")
                        FirebaseDatabase.getInstance().getReference().child("images").child("$num").setValue("${url.toString()}").addOnSuccessListener {
                          Toast.makeText(applicationContext,"Image Uploaded!",Toast.LENGTH_SHORT).show()
                          var intent = Intent(this@ImageAdd,MainActivity::class.java)
                          intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                          startActivity(intent)
                        }
                        FirebaseDatabase.getInstance().getReference().child("images").child("num").setValue("$num")
                          FirebaseDatabase.getInstance().getReference().child("user").child("${FirebaseAuth.getInstance().currentUser?.email?.removeSuffix("@gmail.com")}").child("uploaded").child("uploads").setValue("$num2")

                      }
                    }
                  }
  
        }
      else{
        Toast.makeText(applicationContext,"Nothing To Upload!",Toast.LENGTH_SHORT).show()
          return@upload
        }
       

    }

}