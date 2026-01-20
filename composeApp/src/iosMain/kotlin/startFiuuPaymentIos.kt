package com.example.fiuuxdklibrary

import FiuuPaymentSDK
import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController
import platform.Foundation.NSDictionary

@Suppress("unused")
fun startFiuuPaymentIos(
    paymentParams: NSDictionary
): UIViewController {
    // Convert NSDictionary -> Map<String, Any?>
    val params = paymentParams.toMap()
        .mapKeys { it.key.toString() }

    return ComposeUIViewController {
        FiuuPaymentSDK(
            params = params,
            onPaymentResult = {
                println("Payment success: $it")
            },
            onRequestClose = {
                println("Payment closed")
            }
        )
    }
}