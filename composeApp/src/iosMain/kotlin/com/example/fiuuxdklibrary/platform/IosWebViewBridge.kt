package com.example.fiuuxdklibrary.platform

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.Foundation.NSURLRequest.Companion.requestWithURL
import platform.UIKit.UIViewController
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.darwin.NSObject

class IosWebViewBridge(private val controller: UIViewController) : WebViewBridge {

    @OptIn(ExperimentalForeignApi::class)
    override fun openUrl(
        url: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        onClose: () -> Unit
    ) {
        val webViewController = UIViewController()
        val webView =
            WKWebView(frame = controller.view.bounds, configuration = WKWebViewConfiguration())
        val nsUrl = NSURL(string = url)
        if (nsUrl == null) {
            onFailure("Invalid URL: $url")
            return
        }
        webView.loadRequest(NSURLRequest.requestWithURL(nsUrl))

        webView.navigationDelegate = object : NSObject(), WKNavigationDelegateProtocol {
            override fun webView(webView: WKWebView, didFinishNavigation: WKNavigation?) {
                val currentUrl = webView.URL?.absoluteString
                if (currentUrl?.contains("success=true") == true) {
                    onSuccess()
                    onClose()
                } else if (currentUrl?.contains("success=false") == true) {
                    onFailure("Payment failed")
                    onClose()
                }
            }
        }

        webViewController.view.addSubview(webView)
        controller.presentViewController(webViewController, animated = true, completion = null)
    }
}