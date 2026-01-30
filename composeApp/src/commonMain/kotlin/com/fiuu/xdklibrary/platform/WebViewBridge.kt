package com.fiuu.xdklibrary.platform

interface WebViewBridge {
    /**
     * Opens a URL in a WebView.
     * @param url The payment URL to load.
     * @param onSuccess Called when payment is successful (based on URL redirect or JS).
     * @param onFailure Called when payment fails.
     * @param onClose Called when the WebView is closed by user.
     */

    fun initialize(merchantId: String, sessionId: String)
    fun startCheckOut(onComplete: (String) -> Unit)

    fun openGetUrl(
        url: String,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        onClose: () -> Unit
    )

    fun openPostUrl(
        url: String,
        postData: Map<String, String>,
        onSuccess: () -> Unit,
        onFailure: (String?) -> Unit,
        onClose: () -> Unit
    )
}