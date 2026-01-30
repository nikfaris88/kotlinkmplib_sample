package com.fiuu.xdklibrary.platform

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import java.net.URLEncoder

class AndroidWebViewBridge(private val activity: ComponentActivity) : WebViewBridge {

    private var webView: WebView? = null
    private var merchantId: String = ""
    private var sessionId: String = ""

    override fun initialize(merchantId: String, sessionId: String) {
        this.merchantId = merchantId
        this.sessionId = sessionId
        // Initial setup can happen here if needed before the WebView is created
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun startCheckOut(onComplete: (String) -> Unit) {
//        webView = WebView(activity).apply {
//            settings.javaScriptEnabled = true
//            settings.domStorageEnabled = true
//
//            // Link the JS Bridge we discussed earlier
//            addJavascriptInterface(MastercardJSInterface(onComplete), "AndroidBridge")
//
//            webViewClient = object : WebViewClient() {
//                override fun onPageFinished(view: WebView?, url: String?) {
//                    // Send initialization data to the JS SDK in index.html
//                    val config = """
//                    {
//                        "srcDpaId": "$merchantId",
//                        "sessionId": "$sessionId",
//                        "dpaTransactionOptions": {
//                            "transactionAmount": {"transactionAmount": 10.0, "transactionCurrencyCode": "MYR"}
//                        }
//                    }
//                    """.trimIndent()
//
//                    view?.evaluateJavascript("initClickToPay('$config')", null)
//                }
//            }
//        }
//
////        webView?.loadUrl("file:///android_asset/index.html")
//        webView?.loadUrl("https://test.shidistudio.com/checkout")
//        activity.setContentView(webView!!)

        val url = "https://test.shidistudio.com/checkout?dpaId=82d6652a-60c6-4f2d-9c8a-47b7ff592321"
        val customTabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(false)
            .setUrlBarHidingEnabled(true)
            .setShareState(CustomTabsIntent.SHARE_STATE_OFF)
            .build()

//        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            customTabsIntent.launchUrl(
                activity,
                url.toUri()
            )
        } catch (e: Exception) {
            // fallback
            activity.startActivity(
                Intent(Intent.ACTION_VIEW, url.toUri())
            )
        }
    }

    // Inner class for the JS Bridge
    class MastercardJSInterface(private val onResult: (String) -> Unit) {
        @JavascriptInterface
        fun onPaymentComplete(jsonResponse: String) {
            onResult(jsonResponse)
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    override fun openGetUrl(
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

    @SuppressLint("SetJavaScriptEnabled")
    override fun openPostUrl(
        url: String,
        postData: Map<String, String>,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        onClose: () -> Unit
    ) {
        val webView = WebView(activity)

        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                onSuccess()
            }
        }

        activity.setContentView(webView)

        val encodedPostData = postData.entries.joinToString("&") { (k, v) ->
            "${URLEncoder.encode(k, "UTF-8")}=${URLEncoder.encode(v, "UTF-8")}"
        }.toByteArray(Charsets.UTF_8)

        webView.postUrl(url, encodedPostData)    }
}