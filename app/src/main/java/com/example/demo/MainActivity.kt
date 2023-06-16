package com.example.demo

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.runtime.produceState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.Adapters.ProductsAdapter
import com.example.demo.ApiService.ClientApi
import com.example.demo.ApiService.KtorService
import com.example.demo.Modal.DummyProducts
import com.example.demo.Modal.Product
import com.example.demo.Modal.ResponseModel
import com.example.demo.notes.Note
import com.example.demo.notes.NoteDao
import com.example.demo.notes.NotesDatabase
import com.example.demo.recievers.AirplaneModeChangeReceiver
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {



    private var tabButton: ImageView ?= null
    private var sheetsButton: ImageView ?= null
    lateinit var receiver: AirplaneModeChangeReceiver
    private val webButton: ImageView by lazy {
        findViewById(R.id.webButton)
    }

    private var mvvm: ImageView ?= null

    private val apiService by lazy {
        KtorService.create()
    }

    private fun toastShow(msg: String){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val productsRV: RecyclerView = findViewById(R.id.products_rv)
        val progressBar: ProgressBar = findViewById(R.id.progress_circular)
        productsRV.layoutManager = LinearLayoutManager(this)



        toastShow("OnCreate")

        init()
        initListener()
        handleRetrofit()

//        receiver = AirplaneModeChangeReceiver()
//        IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED).also {
//            registerReceiver(receiver,it)
//        }

        CoroutineScope(Dispatchers.IO).launch{
            for (i in apiService.getProducts()){
                //println(i.title)
            }

            //val img = apiService.getProducts()

        }


        progressBar.visibility = View.VISIBLE
//        tabButton?.setOnClickListener {
//            startActivity(Intent(this, TabActivity::class.java))
//        }
    }




    private fun initListener() {
        tabButton?.setOnClickListener {
            startActivity((Intent(this,FragmentsActivity::class.java)))
        }
        sheetsButton?.setOnClickListener {
            showBottomSheetDialog()
        }

        webButton.setOnClickListener {
            startActivity(Intent(this,WebActivity::class.java))
        }

        mvvm?.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet)

        val copy = bottomSheetDialog.findViewById<LinearLayout>(R.id.copyLinearLayout)
        val share= bottomSheetDialog.findViewById<LinearLayout>(R.id.shareLinearLayout)
        val download = bottomSheetDialog.findViewById<LinearLayout>(R.id.downloadLinearLayout)

        val delete= bottomSheetDialog.findViewById<LinearLayout>(R.id.delete)
        val upload = bottomSheetDialog.findViewById<LinearLayout>(R.id.uploadLinearLaySout)


        bottomSheetDialog.show()

    }

    private fun populateDatabase(db: NotesDatabase){
        val noteDao = db.noteDao()

        noteDao.insert(Note("title 1", "desc 1", 1))
        noteDao.insert(Note("title 2", "desc 2", 2))
        noteDao.insert(Note("title 3", "desc 3", 3))
    }

    private fun init() {
        tabButton = findViewById(R.id.tab_button)
        sheetsButton = findViewById(R.id.bottom_sheets)
        mvvm = findViewById(R.id.mvvm)

        val database = NotesDatabase.getInstance(applicationContext)
        val notesDao: NoteDao = database.noteDao()

        CoroutineScope(Dispatchers.IO).launch{
            populateDatabase(NotesDatabase.getInstance(this@MainActivity))
            val data = notesDao.getNotes()
            println("data $data")
        }






    }

    private fun handleRetrofit(){
        CoroutineScope(Dispatchers.IO).launch {
            ClientApi.getProducts(
                onSuccess = {product: DummyProducts ->
                    findViewById<RecyclerView>(R.id.products_rv).adapter = ProductsAdapter(product) {product: Product ->
                        var intent: Intent = Intent(this@MainActivity,TabActivity::class.java)
                        intent.putExtra("product", product)
                       // println("product $product")
                        startActivity(intent)
                    }

                },
                onError ={
                    Log.i("TAG", "onError: $it")
                }
            )
        }
    }


}

