package com.example.socialmediaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide

class ZoomIn : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_zoom_in)

    var image = findViewById<ImageView>(R.id.imageView2)

    var intent = getIntent()
    var url = intent.getStringExtra("url")

    Glide.with(this).load(url).into(image)


  }
}