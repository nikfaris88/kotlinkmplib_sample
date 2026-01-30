package com.fiuu.xdklibrary.platform

import androidx.compose.runtime.Composable
import com.fiuu.xdklibrary.c2p.C2PWebView

actual class C2PPaymentHandler {
    @Composable
    actual fun PaymentScreen(configJson: String, detailsJson: String, onResult: (Result<String>) -> Unit) {
        println("PaymentScreen: $configJson detailsJson: $detailsJson")
        C2PWebView(configJson, detailsJson, onResult)
    }
}