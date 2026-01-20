package com.example.fiuuxdklibrary.platform

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.core.net.toUri

class AndroidWebViewBridge(private val activity: ComponentActivity) : WebViewBridge {

    @SuppressLint("SetJavaScriptEnabled")
    override fun openUrl(
        url: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        onClose: () -> Unit
    ) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            activity.startActivity(intent)
            onSuccess()
        } catch (e: ActivityNotFoundException) {
            // TNG app not installed, fallback to WebView
            val webView = WebView(activity)
            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    println("FIUU onPageFinished $e")
                }
            }
            webView.loadUrl(url)
            activity.setContentView(webView)
        }
    }
}