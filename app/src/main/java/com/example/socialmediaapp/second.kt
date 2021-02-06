package com.example.socialmediaapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.service.autofill.Sanitizer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import maes.tech.intentanim.CustomIntent

class second(context: Context) : Fragment() {
    
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_second, container, false)

        var text = view.findViewById<TextView>(R.id.textView3)
        var members = view.findViewById<FloatingActionButton>(R.id.floatingActionButton2)
        var addimage = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        var liked = view.findViewById<FloatingActionButton>(R.id.floatingActionButton3)
        var posts = view.findViewById<FloatingActionButton>(R.id.floatingActionButton5)
        var email = FirebaseAuth.getInstance().currentUser?.email.toString()

        text.text = email

        addimage.setOnClickListener {
            var intent = Intent(context,ImageAdd::class.java)
            startActivity(intent)
            CustomIntent.customType(context,"up-to-bottom")
        }

        members.setOnClickListener {
            var intent2 = Intent(context,Members::class.java)
            startActivity(intent2)
            CustomIntent.customType(context,"right-to-left")
        }
        
        liked.setOnClickListener {
            var intent3 = Intent(context,Liked::class.java)
            startActivity(intent3)
            CustomIntent.customType(context,"left-to-right")
        }
        
        liked.setOnLongClickListener(object :View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                Snackbar.make(v!!,"Click To see Liked Photos",Snackbar.LENGTH_SHORT).show()
                return true
            }
    
        })
        addimage.setOnLongClickListener(object :View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                Snackbar.make(v!!,"Click To Upload Images",Snackbar.LENGTH_SHORT).show()
                return true
            }
        
        })
    
        members.setOnLongClickListener(object :View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                Snackbar.make(v!!,"Click To see Members",Snackbar.LENGTH_SHORT).show()
                return true
            }
        
        })

        posts.setOnClickListener {
            var intent = Intent(requireContext(),Posts::class.java)
            startActivity(intent)
            CustomIntent.customType(requireContext(),"fadein-to-fadeout")
        }

        posts.setOnLongClickListener(object :View.OnLongClickListener{
            override fun onLongClick(v: View?): Boolean {
                Snackbar.make(v!!,"Click To see Posts",Snackbar.LENGTH_SHORT).show()
                return true
            }

        })



        return view

    }

    }
