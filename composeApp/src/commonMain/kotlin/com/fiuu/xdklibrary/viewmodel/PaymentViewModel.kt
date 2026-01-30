package com.fiuu.xdklibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fiuu.xdklibrary.data.repository.FiuuPaymentRepository
import com.fiuu.xdklibrary.domain.entity.PaymentChannel
import com.fiuu.xdklibrary.domain.entity.PaymentRequest
import com.fiuu.xdklibrary.domain.entity.toDto
import com.fiuu.xdklibrary.domain.entity.toPaymentRequest
import com.fiuu.xdklibrary.platform.WebViewBridge
import com.fiuu.xdklibrary.ui.model.FormError
import com.fiuu.xdklibrary.ui.model.PaymentError
import com.fiuu.xdklibrary.ui.model.PaymentUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val repo: FiuuPaymentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState = _uiState.asStateFlow()
    private var currentRequest: PaymentRequest? = null
    private var selectedChannel: PaymentChannel? = null

    private var paymentUrl = ""
    private var webViewBridge: WebViewBridge? = null

    fun attachWebViewBridge(bridge: WebViewBridge) {
        webViewBridge = bridge
    }

    fun initialize(params: Map<String, Any?>) {
        val request = params.toPaymentRequest()
        this.currentRequest = request

        _uiState.update { it.copy(
            amount = request.mpAmount?.toDoubleOrNull()?.let { (it * 100).toLong() } ?: 0L,
            currency = request.mpCurrency?: "",
            name = request.mpBillName ?: "",
            email = request.mpBillEmail ?: "",
            mobile = request.mpBillMobile ?: "",
            description = request.mpBillDescription ?: ""
        )}
    }

    fun updateName(value: String) {
        _uiState.update { it.copy(
            name = value,
            formError = if (it.formError == FormError.EMPTY_NAME)
                null else it.formError
        ) }
    }

    fun updateEmail(value: String) {
        _uiState.update { it.copy(
            email = value,
            formError = if (it.formError == FormError.INVALID_EMAIL) null else it.formError
        ) }
    }

    fun updatePhone(value: String) {
        _uiState.update { it.copy(
            mobile = value,
            formError = if ( it.formError == FormError.INVALID_PHONE ) null else it.formError
        ) }
    }

    fun updateDescription(value: String) {
        _uiState.update { it.copy(description = value) }
    }

    fun onChannelSelected(channel: PaymentChannel) {
        selectedChannel = channel
        currentRequest = currentRequest?.copy(
            mpChannel = channel.name
        )

        _uiState.update { it.copy(selectedChannel = channel) }
    }

    fun setPaymentError(error: PaymentError) {
        _uiState.update {
            it.copy(paymentError = error)
        }
    }

    fun startPayment() {
        val request = currentRequest ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }

            val state = _uiState.value

            val finalRequest = request.copy(
                // Update user details in case they edited them in the SDK UI
                mpBillName = state.name,
                mpBillEmail = state.email,
                mpBillMobile = state.mobile,
                mpBillDescription = state.description,

                // Set the selected payment method
                mpChannel = request.mpChannel,

                // Keep the structured objects in sync
                mpAmount = request.mpCurrency,
                mpOrderID = request.mpOrderID,
            )

            val testRequest = request.toDto(
                txnType = "SALE",
                uat = request.mpSandboxMode == true,
                lVersion = "2"
            )
            val result = repo.generatePayment(testRequest)

            println("RESULT API: $result")
            if (result.status) {
                paymentUrl = result.requestURL
                val bridge = webViewBridge ?: run {
                    return@launch
                }
                openPayment(bridge)
            }

//            _uiState.update {
//                it.copy(
//                    loading = false,
//                    paymentError = result.exceptionOrNull()?.toPaymentError()
//                )
//            }

//            if (result.isSuccess) {
//                result.getOrNull()?.let { response -> println("RESPONSE: $response") }
//            }
//
//            result.onSuccess { url ->
//                // Here you can open WebView or pass URL to client
//                _uiState.update { it.copy(paymentSuccess = null) }
//            }.onFailure { e ->
//                _uiState.update { it.copy(paymentError = e.toPaymentError()) }
//            }
        }
    }

    fun openPayment(webViewBridge: WebViewBridge) {
//        val request = currentRequest ?: return
//        val url = buildPaymentUrl() // TNG URL

//        webViewBridge.openPostUrl(
//            url = url,
//            postData =
//        )
//        webViewBridge.openGetUrl(
//            url = paymentUrl,
//            onSuccess = {
//                println("FIUU OPEN SUCCESS")
//            },
//            onFailure = { error ->
//                println("FIUU  OPEN ERROR $error")
//                        },
//            onClose = { println("FIUU CLOSEDDD") }
//        )
    }

//    private fun buildPaymentUrl(url: String): String {
//        return url
//        return "https://m.tngdigital.com.my/s/cashier/index.html?bizNo=20260120211212800110171526332462757&timestamp=1768881001715&merchantId=217120000009798239785&sign=PvuZheWb%252BoL1C72k7kepBJ6urYilpTp7dtg7T34OjjveeshvfQPHae12wBDTMEZ2QyGdeAfiMiuySI34RjtOMT0AqUXy7VgKOfJDw0enJyOUAWsk56W3%252BKzZeTqtR1VtTiPXitfykcPJ%252B8xzx4C%252B0KTckq30Kw%252BWYpeUKSvVZF4wmRDkGYVGbTlhpGhj12AGUmNwKWIMDmwUQNSYWobLfI1Rcm%252F174MKYVPG6sQC%252FWpP2XxYsIKKiP5N8Mw35o1oPGUWT3IDR5E32%252B%252BIBg3q92gqj1DRbABVVCAWX97IYcZal15sou20drIagIAw6ALOmMZ5ShRT8x9ZFiAz1xopow%253D%253D&forceInstallVer2=true"
//        return "https://m.tngdigital.com.my/s/cashier/index.html?" +
//                "amount=${request.mpAmount}&order_id=${request.mpOrderID}" +
//                "&username=${request.mpUsername}&channel=${request.mpChannel}" +
//                "&currency=${request.mpCurrency ?: "MYR"}"
//    }

    private fun Throwable.toPaymentError(): PaymentError {
        return when (this) {
            is IllegalArgumentException -> PaymentError.InvalidChannel(message ?: "")
            else -> PaymentError.Unknown()
        }
    }

    fun clearPaymentError() {
        _uiState.update {
            it.copy(paymentError = null)
        }
    }
}