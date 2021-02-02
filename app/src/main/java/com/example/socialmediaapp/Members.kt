package com.example.socialmediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Members : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_members)
    var list = findViewById<ListView>(R.id.list)

    var array = arrayListOf<String>()
    
    var adap = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array)

    list.adapter = adap

    var ref = FirebaseDatabase.getInstance().getReference().child("user")

    var getdata = object:ValueEventListener{
      override fun onDataChange(snapshot: DataSnapshot) {
        for(i in snapshot.children){
          var names = i.child("email").getValue().toString()
          if(names=="null"){

          }
          else{
            array.add(names)
            adap.notifyDataSetChanged()
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