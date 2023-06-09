package com.example.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.demo.fragments.CallsFragment
import com.example.demo.fragments.HomeFragment
import com.example.demo.fragments.SportFragment

class FragmentsActivity : AppCompatActivity() {

    private lateinit var frameLayout: FrameLayout
    private var status: Button ?= null
    private var calls: Button ?= null
    private var messages: Button ?= null

    private var rootString = "root_fragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        init()
        initListeners()
        // default fragment
        loadFrag(HomeFragment(),0)




    }

    private fun initListeners() {
        messages?.setOnClickListener {
            loadFrag(HomeFragment(),0)
        }
        calls?.setOnClickListener {
            loadFrag(SportFragment(),1)
        }
        status?.setOnClickListener {
            loadFrag(CallsFragment(),1)
        }
    }

    private fun loadFrag(fragment_name: Fragment, flag: Int) {
        var fm: FragmentManager = supportFragmentManager
        var ft: FragmentTransaction = fm.beginTransaction()

        if (flag == 0){
            ft.add(R.id.FL, fragment_name)
            fm.popBackStack(rootString, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            ft.addToBackStack(rootString)
        }
        else{
            ft.replace(R.id.FL, fragment_name)
            ft.addToBackStack(null)
        }
        ft.commit()
    }

    private fun init() {
        findViewById<FrameLayout>(R.id.FL)
        status = findViewById(R.id.status)
        calls = findViewById(R.id.calls)
        messages = findViewById(R.id.msgs)
    }
}