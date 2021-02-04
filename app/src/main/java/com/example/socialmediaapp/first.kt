package com.example.socialmediaapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class first(context:Context) : Fragment() {
   
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_first, container, false)
        var recycle = view.findViewById<RecyclerView>(R.id.recycle)
        var prog = view.findViewById<ProgressBar>(R.id.progressBar2)
        var array = arrayListOf<String>()
    
       
        recycle.layoutManager = GridLayoutManager(context,2)
        recycle.visibility = View.GONE
        prog.visibility = View.VISIBLE
        
        var adapter = Manager(context!!,array)
        recycle.adapter = adapter
    
    
    
        var ref = FirebaseDatabase.getInstance().getReference()

       
        var number=0
        
        
       ref.addValueEventListener(object:ValueEventListener{
           override fun onDataChange(snapshot: DataSnapshot) {
               for(i in snapshot.children){
                   var num = i.child("num").getValue().toString()
                   if(num=="null"){
                
                   }
                   else{
                           prog.visibility = View.GONE
                           recycle.visibility = View.VISIBLE
                       var num = num.toInt()
                       array.clear()
                       while(0<num){
                           var url = i.child("$num").getValue().toString()
                           Log.d("Main2","Done!!")
                           array.add(url)
                           Log.d("Main2","$num")
                           Log.d("Main2","${array[0]}")
                           adapter.notifyDataSetChanged()
                           num--
                       }
                   }
               }
           }
    
           override fun onCancelled(error: DatabaseError) {
               Toast.makeText(context,"Error!",Toast.LENGTH_SHORT).show()
           }
    
       })

        
        return view
    }

}