package com.example.socialmediaapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import maes.tech.intentanim.CustomIntent

class Manager2(private var context: Context, private var array: ArrayList<String>):RecyclerView.Adapter<Manager2.ViewHolder>() {

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        var image = itemView.findViewById<ImageView>(R.id.imageView3)
        var share = itemView.findViewById<FloatingActionButton>(R.id.floatingActionButton4)
       
      init {
        image.setOnClickListener {
          var intent = Intent(context,ZoomIn::class.java)
          intent.putExtra("url","${array[adapterPosition]}")
          context.startActivity(intent)
          CustomIntent.customType(context,"fadein-to-fadeout")
        }
      share.setOnClickListener {
        var intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"${array[adapterPosition]}")
        var chooser = Intent.createChooser(intent,"Complete Action Using...")
        context.startActivity(chooser)
      }
      }
      
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var view = LayoutInflater.from(context).inflate(R.layout.card2,parent,false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       var currentpos = array[position]
        Glide.with(context).load(currentpos).into(holder.image)
    }
    
    override fun getItemCount() = array.size
    
    
}
