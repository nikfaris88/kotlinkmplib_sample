package com.fiuu.xdklibrary.platform

import FiuuPaymentSDK
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import com.fiuu.xdklibrary.data.remote.dto.PaymentResponseDto
import localWebViewBridge

@Composable
fun ProvideFiuuPaymentSDK(
    params: Map<String, Any?>,
    onPaymentResult: (PaymentResponseDto) -> Unit,
    onRequestClose: () -> Unit
) {
    val activity = LocalActivity.current as ComponentActivity

    val webViewBridge = remember(activity) {
        AndroidWebViewBridge(activity)
    }

    CompositionLocalProvider(
        localWebViewBridge provides webViewBridge
    ) {
        FiuuPaymentSDK(
            params = params,
            onPaymentResult = onPaymentResult,
            onRequestClose = onRequestClose
        )
    }
}