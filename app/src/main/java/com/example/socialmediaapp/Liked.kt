package com.example.socialmediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Liked : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_liked)
    var recycle = findViewById<RecyclerView>(R.id.recyclerView)
    var prog = findViewById<ProgressBar>(R.id.progressBar3)
    prog.visibility = View.VISIBLE
    recycle.visibility = View.GONE
    var array = arrayListOf<String>()
    var email = FirebaseAuth.getInstance().currentUser?.email?.removeSuffix("@gmail.com").toString()
    var ref = FirebaseDatabase.getInstance().getReference().child("user").child("$email")
    recycle.layoutManager = LinearLayoutManager(this)
    var adapter = Manager2(this,array)
    recycle.adapter = adapter
    var getdata = object:ValueEventListener{
      override fun onDataChange(snapshot: DataSnapshot) {
        for(i in snapshot.children){
          var likes = i.child("Likes").getValue().toString()
          if(likes=="null"){

          }
          else{
          prog.visibility = View.GONE
          recycle.visibility = View.VISIBLE
          var likes = likes.toInt()
            while (0<likes){
              var url = i.child("$likes").getValue().toString()
              array.add(url)
              adapter.notifyDataSetChanged()
              likes--
            }
          }
        }
      }

      override fun onCancelled(error: DatabaseError) {
        TODO("Not yet implemented")
      }

    }
    ref.addValueEventListener(getdata)
  }
}