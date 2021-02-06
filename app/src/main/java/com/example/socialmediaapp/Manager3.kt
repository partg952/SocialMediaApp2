package com.example.socialmediaapp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.floatingactionbutton.FloatingActionButton
import maes.tech.intentanim.CustomIntent

class Manager3(private var context: Posts, private var array: ArrayList<String>) : RecyclerView.Adapter<Manager3.ViewHolder>() {

    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        var image = itemView.findViewById<ImageView>(R.id.image)
        var share = itemView.findViewById<FloatingActionButton>(R.id.imageButton2)
        var prog = itemView.findViewById<ProgressBar>(R.id.progressBar)
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
        var view = LayoutInflater.from(context).inflate(R.layout.card,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var currentpos = array[position]
        holder.prog.visibility = View.VISIBLE
        Glide.with(context).load(currentpos).listener(object: RequestListener<Drawable?> {
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
