package com.example.demo.web

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast

object WebAppInterface {

    @JavascriptInterface
    fun onClicked() {
        Log.d(TAG, "Help button clicked")
    }
}

fun bind(webView: WebView) {
    webView.addJavascriptInterface(WebAppInterface, "Android")
}