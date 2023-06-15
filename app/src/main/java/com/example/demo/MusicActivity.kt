package com.example.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.demo.services.MusicService

class MusicActivity : AppCompatActivity(), View.OnClickListener {

    private var start: ImageView? = null
    private var stop: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        init()

        start!!.setOnClickListener {
            Toast.makeText(this@MusicActivity,"playing",Toast.LENGTH_LONG).show()
        }
        stop!!.setOnClickListener { this }


    }
    private fun init(){
        start = findViewById(R.id.music_play)
        stop = findViewById(R.id.music_stop)
    }
    override fun onClick(v: View?) {
        if (v == start){
            startService(Intent(this,MusicService::class.java))
        }

        if (v == stop){
            stopService(Intent(this,MusicService::class.java))
        }
    }
}