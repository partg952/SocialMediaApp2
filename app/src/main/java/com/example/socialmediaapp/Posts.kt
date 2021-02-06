package com.example.socialmediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Posts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)
        var array = arrayListOf<String>()
        var recycle = findViewById<RecyclerView>(R.id.re45)
       var adapter = Manager3(this,array)
        recycle.layoutManager = GridLayoutManager(this,2)
        recycle.adapter = adapter
        var getdata = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(i in snapshot.children){
                    var num = i.child("uploads").getValue().toString()
                    Log.d("Good","$num")
                    if(num=="null"){

                    }
                    else{
                    var num=num.toInt()
                        array.clear()
                        while (num>0){
                            var string = i.child("$num").getValue().toString()
                            array.add(string)
                            Log.d("Lg","$string")
                            adapter.notifyDataSetChanged()
                            num--
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        FirebaseDatabase.getInstance().getReference().child("user").child("${FirebaseAuth.getInstance().currentUser?.email?.removeSuffix("@gmail.com")}").addValueEventListener(getdata)
    }
}