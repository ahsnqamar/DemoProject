package com.example.demo

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings.Global
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.example.demo.Adapters.TabAdapter
import com.example.demo.Modal.Product
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.net.URL

class TabActivity : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    private val getImage: ImageView by lazy {
        findViewById(R.id.image_get)
    }

    val musicScreen: ImageView by lazy {
        findViewById(R.id.music_screen)
    }

    private val pickImage = 100
    private var imageUri: Uri? = null
    private lateinit var image: ImageView
    private val imageUrl = "https://media.istockphoto.com/id/1421301993/photo/shopping-cart-sign-on-the-cube-shape-dice.jpg?s=2048x2048&w=is&k=20&c=8bSRbfu6W5QWIaHRRxWnGJ07MKPfOS3fzKz6KhnG5cA="

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

//        var product_recieved: Product = intent.getSerializableExtra("product") as Product
//        var category = product_recieved.category
//        println(category)

        init()

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Home"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Sport"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = TabAdapter(this,supportFragmentManager,2)
        viewPager?.adapter = adapter
        viewPager?.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
//        tabLayout!!.setupWithViewPager(viewPager)

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    println(tab.position)
                    viewPager?.currentItem = tab.position
                }

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }



        })

        getImage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        musicScreen.setOnClickListener {
            startActivity(Intent(this,MusicActivity::class.java))
        }

        image.setOnClickListener {

        }

        getBitmapFromUrl()

        //main()

        randomWork()


    }

    private suspend fun doSomething() : String {
        delay(3000L)
        return "returned "
    }

    override fun startActivityForResult(intent: Intent, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
        if (requestCode == pickImage){
            imageUri = intent?.data
            getImage.setImageURI(imageUri)
        }
    }

    private fun init() {
        tabLayout = findViewById(R.id.tab_layout)
        image = findViewById(R.id.getImage)
        //viewPager = findViewById(R.id.view_pager)
    }

    private fun getBitmapFromUrl(){
        println("h1")
        val job = CoroutineScope(Dispatchers.IO).launch {
            println("h2")
            val bitmap = getBitmap()
            println("h3")
            withContext(Dispatchers.Main){
                image.setImageBitmap(bitmap)
            }
            println("h4")
        }
        println("h5")
        job.cancel()
        println("h6")
    }

    private fun getBitmap(): Bitmap ? {
        val url = URL(imageUrl)
        return BitmapFactory.decodeStream(url.openConnection().getInputStream())
    }

    private fun main() = CoroutineScope(Dispatchers.Default).launch(){
        println("My program runs...: ${Thread.currentThread().name}")

        launch { // starting a coroutine
            println("1")  // calling the long running function
        }
        launch{
            delay(2000L)
            println("2")

        }

        launch{
            println("3")
        }

        launch {
            longRunningTask()
        }

        for (i in 1..10){
            println("i $i")
        }
        println("5")

        //longRunningTask()

        println("My program run ends...: ${Thread.currentThread().name}")
    }

    private suspend fun longRunningTask(){
        println("executing longRunningTask on...: ${Thread.currentThread().name}")
        delay(1000L)  // simulating the slow behavior by adding a delay
        println(
            "longRunningTask ends on thread ...: ${Thread.currentThread().name}")
    }

    private fun randomWork(){
        val job = CoroutineScope(Dispatchers.Default).launch {
            Log.d(TAG,"Starting the long calculation...")

            withTimeout(8000L){
                // running the loop from 30 to 40
                for(i in 30..40)
                {
                    // using isActive functionality to check whether the
                    // coroutine is active or not
                    if(isActive)
                        Log.d(TAG, "Result for i =$i : ${fib(i)}")
                }
                Log.d(TAG, "Ending the long calculation...")
            }
        }
    }

    private fun fib(n:Int):Long
    {
        return if(n==0) 0
        else if(n==1) 1
        else fib(n-1) + fib(n-2)
    }



}