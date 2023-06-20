package com.example.demo

import android.annotation.SuppressLint
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.get

class FirebaseConfig {
    private lateinit var firebaseRemoteConfig: FirebaseRemoteConfig
    private lateinit var configSettings: FirebaseRemoteConfigSettings
    var cacheExpiration: Long = 43200

    var imageUrl = ""

    @SuppressLint("NotConstructor")
    fun FirebaseConfig(){
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        configSettings = FirebaseRemoteConfigSettings.Builder().build()

        firebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

    }

    fun fetchNewImage(): String{
        firebaseRemoteConfig.fetch(cacheExpiration)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    firebaseRemoteConfig.fetchAndActivate()
                }
                imageUrl = firebaseRemoteConfig["new_image"].toString()
            }

        return imageUrl
    }
}