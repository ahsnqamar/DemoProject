package com.example.demo

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.demo.web.WebAppInterface


class WebActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private val progressBar: ProgressBar by lazy {
        findViewById(R.id.progress_bar)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                loadJs(view)
                Toast.makeText(this@WebActivity,"Website loaded",Toast.LENGTH_SHORT).show()
            }
        }

        val webChrome = object : WebChromeClient(){
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                println("progress $newProgress %")
            }
        }

        val webView = findViewById<WebView>(R.id.web_view)
        webView.webViewClient = webViewClient
        webView.webChromeClient = webChrome
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface, "Android")

//        webView.loadUrl("http://cbseacademic.in/SQP_CLASSXII_2016_17.html")
//        webView.setDownloadListener { url, userAgent, contentDisposition, mimeType, contentLength ->
//            val request = DownloadManager.Request(Uri.parse(url))
//            request.setMimeType(mimeType)
//            request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(url))
//            request.addRequestHeader("User-Agent", userAgent)
//            request.setDescription("Downloading file...")
//            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
//            request.allowScanningByMediaScanner()
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//            request.setDestinationInExternalFilesDir(
//                this@WebActivity,
//                Environment.DIRECTORY_DOWNLOADS,
//                ".png"
//            )
//            val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//            dm.enqueue(request)
//            Toast.makeText(applicationContext, "Downloading File", Toast.LENGTH_LONG).show()
//        }

        webView.loadUrl("https://www.geeksforgeeks.org")

//        webView.settings.javaScriptEnabled = true
//        webView.addJavascriptInterface(WebAppInterface(this),"Android")
//        webView.webViewClient = MyWebViewClient(progressBar = progressBar)
//
//        webView.loadUrl("https://www.geeksforgeeks.org")
//        webView.webViewClient = MyWebViewClient(progressBar = progressBar)

//        webView.loadData( "<html>\n" +
//                "  <head>\n" +
//                "    <!-- Tip: Use relative URLs when referring to other in-app content to give\n" +
//                "              your app code the flexibility to change the scheme or domain as\n" +
//                "              necessary. -->\n" +
//                "    <link rel=\"stylesheet\" href=\"/assets/stylesheet.css\">\n" +
//                "  </head>\n" +
//                "  <body>\n" +
//                "    <p>This file was loaded from in-app content</p>\n" +
//                "<input type=\"button\" value=\"Say hello\" onClick=\"showAndroidToast('Hello Android!')\" />" +
//                "  </body>\n" +
//                "<script type=\"text/javascript\">\n" +
//                "    function showAndroidToast(toast) {\n" +
//                "        Android.showToast(toast);\n" +
//                "    }\n" +
//                "</script>" +
//                "</html>",
//            "text/html", "UTF-8")

    }
    private fun loadJs(webView: WebView) {
        webView.loadUrl(
            """javascript:(function f() {
        var btns = document.getElementsByTagName('button');
        for (var i = 0, n = btns.length; i < n; i++) {
          if (btns[i].getAttribute('aria-label') === 'Support') {
            btns[i].setAttribute('onclick', 'Android.onClicked()');
          }
        }
      })()"""
        )
    }


}