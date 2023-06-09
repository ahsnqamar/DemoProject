package com.example.demo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.Adapters.ProductsAdapter
import com.example.demo.ApiService.ClientApi
import com.example.demo.Modal.DummyProducts
import com.example.demo.Modal.Product
import com.example.demo.ui.theme.DemoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {



    var tabButton: ImageView ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val productsRV: RecyclerView = findViewById(R.id.products_rv)
        val progressBar: ProgressBar = findViewById(R.id.progress_circular)
        productsRV.layoutManager = LinearLayoutManager(this)

        init()
        initListener()
        handleRetrofit()


        progressBar.visibility = View.VISIBLE
//        tabButton?.setOnClickListener {
//            startActivity(Intent(this, TabActivity::class.java))
//        }
    }

    private fun initListener() {
        tabButton?.setOnClickListener {
            startActivity((Intent(this,FragmentsActivity::class.java)))
        }
    }

    private fun init() {
        tabButton = findViewById(R.id.tab_button)
    }

    private fun handleRetrofit(){
        CoroutineScope(Dispatchers.IO).launch {
            ClientApi.getProducts(
                onSuccess = {product: DummyProducts ->
                    findViewById<RecyclerView>(R.id.products_rv).adapter = ProductsAdapter(product) {product: Product ->
                        var intent: Intent = Intent(this@MainActivity,TabActivity::class.java)
                        intent.putExtra("product", product)
                        println("product $product")
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

