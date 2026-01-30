package com.fiuu.xdklibrary.platform

import androidx.compose.runtime.Composable

expect class C2PPaymentHandler() {
    @Composable
    fun PaymentScreen(configJson: String, detailsJson:String, onResult: (Result<String>) -> Unit)
}