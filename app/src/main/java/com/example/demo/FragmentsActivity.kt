package com.example.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
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
//        loadFragment(homeFragment)


        initListener()

        //bottomNav.getOrCreateBadge(R.id.Calls).number = 2




    }

    private fun initListener(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.findNavController()
        findViewById<BottomNavigationView>(R.id.bottomNav).setupWithNavController(navController)
    }

//    private fun initListener() {
//        bottomNav.setOnItemSelectedListener {
//            when(it.itemId){
//                R.id.home -> {
//                    println("hello")
//                    //loadFragment(homeFragment)
//                    //Navigation.findNavController(this,R.id.my_nav_host_fragment).navigate(R.id.sports_to_home)
//                    val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
//                    navHostFragment.navController.navigate(R.id.sports_to_home)
//
//                    true
//                }
//                R.id.Calls -> {
//                    println("hi")
////                    loadFragment(callsFragment)
////                    bottomNav.getOrCreateBadge(R.id.Calls).clearNumber()
//                    //Navigation.findNavController(this,R.id.my_nav_host_fragment).navigate(R.id.home_to_calls)
//                    val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
//                    navHostFragment.navController.navigate(R.id.home_to_calls)
//
//                    true
//                }
//                R.id.Settings -> {
////                    loadFragment(settingsFragment)
//                    //Navigation.findNavController(this,R.id.my_nav_host_fragment).navigate(R.id.calls_to_sport)
//                    val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
//                    navHostFragment.navController.navigate(R.id.calls_to_sport)
//
//                    true
//                }
//
//                else -> {
//                    true
//                }
//            }
//        }
//
//    }

    private fun init(){
        bottomNav = findViewById(R.id.bottomNav)

    }

//    private fun loadFragment(fragment: Fragment){
//        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.frame_container,fragment)
//        transaction.commit()
//    }

}
