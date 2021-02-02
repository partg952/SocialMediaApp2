package com.example.socialmediaapp

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import maes.tech.intentanim.CustomIntent

class Manager(private var context: Context, private var array: ArrayList<String>) : RecyclerView.Adapter<Manager.ViewHolder>() {

    
    
    
    
    
    
    
    
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
    
        
    
        var image = itemView.findViewById<ImageView>(R.id.image)
        var share = itemView.findViewById<ImageButton>(R.id.imageButton2)
        var prog = itemView.findViewById<ProgressBar>(R.id.progressBar)
        
        init {
            var integer = 0
    
            var email = FirebaseAuth.getInstance().currentUser?.email?.removeSuffix("@gmail.com").toString()
    
    
            var ref = FirebaseDatabase.getInstance().getReference().child("user").child("$email")
    
            var getdata = object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    for(i in snapshot.children){
                        var likecount = i.child("Likes").getValue().toString()
                        if(likecount=="null"||likecount=="0"){
                    
                        }
                        else{
                            Log.d("Main2","$likecount")
                            integer = likecount.toInt()
                        }
                    }
                }
        
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
        
            }
    
            
            ref.addValueEventListener(getdata)
    
    
            share.setOnClickListener {
             var currentpos = array[adapterPosition]
             var inent = Intent(Intent.ACTION_SEND)
             inent.type = "text/plain"
             inent.putExtra(Intent.EXTRA_TEXT,"$currentpos")
            var chooser = Intent.createChooser(inent,"Complete Action Using...")
            context.startActivity(chooser)
         }
            image.setOnClickListener{
                var intent = Intent(context,ZoomIn::class.java)
                intent.putExtra("url",array[adapterPosition])
                context.startActivity(intent)
                CustomIntent.customType(context,"fadein-to-fadeout")

            }
            
            image.setOnLongClickListener(object :View.OnLongClickListener{
                override fun onLongClick(v: View?): Boolean {
                    integer++
                    FirebaseDatabase.getInstance().getReference().child("user").child("$email").child("LikeSystem").child("$integer")
                            .setValue(array[adapterPosition])
                    
                    FirebaseDatabase.getInstance().getReference().child("user").child("$email").child("LikeSystem").child("Likes")
                            .setValue(integer)
                    
                    Snackbar.make(v!!,"Added To Favourites!",Snackbar.LENGTH_SHORT).show()
                    
                    return true
                }
    
            })
        }
    }
    
    
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    var view = LayoutInflater.from(context).inflate(R.layout.card,parent,false)
    return ViewHolder(view)
    
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.prog.visibility = View.VISIBLE
        var currentpos = array[position]
        Log.d("Main2","wooh")
        Glide.with(context).load(currentpos).listener(object :RequestListener<Drawable?>{
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable?>?, isFirstResource: Boolean): Boolean {
                holder.prog.visibility = View.GONE
                holder.image.setImageResource(R.drawable.ic_baseline_error_24)
                return false
            }
    
            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable?>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
               holder.prog.visibility = View.GONE
                return false
            }
    
        }).into(holder.image)
    }

    override fun getItemCount() = array.size

}
