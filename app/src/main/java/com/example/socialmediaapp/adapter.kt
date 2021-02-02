package com.example.socialmediaapp

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class adapter(private var context: Context, fm:FragmentManager):FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
      return when(position){
           0->{
               first(context)
           }
           1->{
                second(context)
           }
          else->{
              first(context)
          }

       }


    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0->{
                "Posts"
            }
            1->{
                "+"
            }
            else->{
                "Posts"
            }
        }
        return super.getPageTitle(position)
    }


}