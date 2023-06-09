package com.example.demo.Adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demo.fragments.HomeFragment
import com.example.demo.fragments.SportFragment

class TabAdapter (private val myContext: Context, fm:FragmentManager, private var totalTabs: Int):FragmentPagerAdapter(fm){
    override fun getCount(): Int {
        return totalTabs
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> {
                println("in home fragment adapter")
                return HomeFragment()
            }
            1 -> {
                return SportFragment()
            }


        }
        return HomeFragment()
    }


}