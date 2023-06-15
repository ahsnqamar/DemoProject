package com.example.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.demo.fragments.CallsFragment
import com.example.demo.fragments.HomeFragment
import com.example.demo.fragments.SportFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentsActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    private val homeFragment by lazy { HomeFragment() }
    private val callsFragment by lazy { CallsFragment() }
    private val settingsFragment by lazy { SportFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        init()
        loadFragment(homeFragment)
        initListener()

        bottomNav.getOrCreateBadge(R.id.Calls).number = 2




    }

    private fun initListener() {
        bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    println("hello")
                    loadFragment(homeFragment)
                    true
                }
                R.id.Calls -> {
                    println("hi")
                    loadFragment(callsFragment)
                    bottomNav.getOrCreateBadge(R.id.Calls).clearNumber()
                    true
                }
                R.id.Settings -> {
                    loadFragment(settingsFragment)
                    true
                }

                else -> {loadFragment(HomeFragment())
                    true
                }
            }
        }

    }

    private fun init(){
        bottomNav = findViewById(R.id.bottomNav)

    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container,fragment)
        transaction.commit()

    }

}
