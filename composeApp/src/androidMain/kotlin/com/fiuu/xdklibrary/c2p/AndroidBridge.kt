package com.fiuu.xdklibrary.c2p

import android.util.Log
import android.webkit.JavascriptInterface

class AndroidBridge(
    private val onSuccess: (String) -> Unit,
    private val onError: (String) -> Unit,
    private val onShidi: (Any) -> Unit
) {

    @JavascriptInterface
    fun onSuccess(resultJson: String) {
        Log.d("C2P", "Success: $resultJson")
        onSuccess(resultJson)
    }

    @JavascriptInterface
    fun onError(errorJson: String) {
        Log.d("C2P", "Error: $errorJson")
        onError(errorJson)
    }

    @JavascriptInterface
    fun onShidi(payload: Any) {
        Log.d("C2P", "onShidi: $payload")
        onShidi(payload)

    }
}