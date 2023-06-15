package com.example.demo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings.Global
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        CoroutineScope(Dispatchers.IO).launch {
            launch{ val call = doSomething()
            println(call)
            }
            println("34")
            println("call")
            main()

        }




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

    private suspend fun main(): Unit = coroutineScope{

        launch(Dispatchers.Default) {
            println("Default : Thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.Unconfined) {
            println("Unconfined : Thread ${Thread.currentThread().name}")
        }

        launch(Dispatchers.IO) {
            println("IO : Thread ${Thread.currentThread().name}")
        }
    }



}