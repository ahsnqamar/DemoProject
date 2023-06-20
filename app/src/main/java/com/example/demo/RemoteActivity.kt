package com.example.demo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.demo.application.MyApplication
import com.example.demo.databinding.ActivityRemoteBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MediaAspectRatio
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.get
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.w3c.dom.Text
import java.net.URL

class RemoteActivity : AppCompatActivity() {

    private lateinit var text: TextView
    private val image: ImageView by lazy {
        findViewById(R.id.imageViewImg)
    }

    private lateinit var nativeAdContainer : ConstraintLayout

    //private var mInterstitialAd: InterstitialAd ?= null
    //private lateinit var adRequest: AdRequest

    lateinit var mAdView: AdView

    lateinit var binding: ActivityRemoteBinding



    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    //private lateinit var configSettings: FirebaseRemoteConfigSettings
    private var cacheExpiration: Long =10
    private var imageUrl = "https://searchengineland.com/wp-content/seloads/2017/03/GoogleAd_1920.jpg"
    private var imageText = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRemoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        init()



        val  remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 10
        }

        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        firebaseRemoteConfig = remoteConfig
        //fetchNewImage()

        //adRequest = AdRequest.Builder().build()

//        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712" , adRequest, object : InterstitialAdLoadCallback(){
//            override fun onAdFailedToLoad(p0: LoadAdError) {
//                super.onAdFailedToLoad(p0)
//                mInterstitialAd = null
//            }
//
//            override fun onAdLoaded(p0: InterstitialAd) {
//                super.onAdLoaded(p0)
//                mInterstitialAd = p0
//                if (mInterstitialAd != null) {
//                    mInterstitialAd?.show(this@RemoteActivity)
//                } else {
//                    Log.d("AD", "The interstitial ad wasn't ready yet.")
//                }
//            }
//        })



        // BANNER ADS
        //mAdView = findViewById(R.id.adView)
        //MobileAds.initialize(this){}
        //val adSize = AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(this, 320)
//        mAdView.setAdSize(adSize)
//        mAdView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        //val adRequest = AdRequest.Builder().build()
        //mAdView.loadAd(adRequest)

//        Handler(Looper.getMainLooper()).postDelayed({
//            mAdView.visibility  = View.GONE
//        },10000)

        // NATIVE ADS

        nativeAdContainer = findViewById(R.id.nativeAdContainer)
        loadNativeAd()

        // App open ADS
        //(application as MyApplication).AppOpenAdManager(application).showAdIfAvailable(this)

    }

    private fun init(){


        //adView = findViewById(R.id.adView)
    }

    private fun loadNativeAd(){
        val adLoader = AdLoader.Builder(this,"ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { nativeAd ->
                val nativeAdView = LayoutInflater.from(this).inflate(R.layout.native_ad_layout, null) as NativeAdView

                populateNativeAdView(nativeAd, nativeAdView)
                nativeAdContainer.addView(nativeAdView)
            }
            .withAdListener(object: AdListener(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                }
            })
            .withNativeAdOptions(
                NativeAdOptions.Builder()
                    //.setRequestCustomMuteThisAd(true)
                    .build()
            )
            .build()
        println("ad is about to load")
        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun populateNativeAdView(nativeAd: NativeAd, nativeAdView: NativeAdView) {
        nativeAdView.apply {
            findViewById<TextView>(R.id.adTitleTextView).text = nativeAd.headline
            // Set the ad image
            findViewById<ImageView>(R.id.adImageView).setImageDrawable(nativeAd.mediaContent?.mainImage)

            // Set the ad body
            findViewById<TextView>(R.id.adBodyTextView).text = nativeAd.body

            // Set the call-to-action button
            findViewById<Button>(R.id.adCTAButton).text = nativeAd.callToAction

            //setNativeAd(nativeAd)
        }
    }


    private fun fetchNewImage(): String{
        firebaseRemoteConfig.fetch(cacheExpiration)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    firebaseRemoteConfig.fetchAndActivate()
                }
                imageUrl = firebaseRemoteConfig.getString("new_image")
                imageText = firebaseRemoteConfig.getString("new_text")
                println("imageText $imageText")
                println("imageUrl $imageUrl")

                Picasso.get().load(imageUrl).into(image)
                text.text = imageText
            }

        return imageUrl
    }

}