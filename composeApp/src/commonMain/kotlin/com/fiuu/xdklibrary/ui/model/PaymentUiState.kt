package com.fiuu.xdklibrary.ui.model

import com.fiuu.xdklibrary.data.remote.dto.PaymentResponseDto
import com.fiuu.xdklibrary.domain.entity.PaymentChannel

data class PaymentUiState(
    val amount: Long = 1000L,
    val currency: String = "",
    val orderId: String = "1759893168562",
    val name: String = "",
    val email: String = "",
    val mobile: String = "",
    val description: String = "",
    val loading: Boolean = false,
    val formError: FormError? = null,
    val paymentSuccess: PaymentResponseDto? = null,
    val paymentError: PaymentError? = null,
    val selectedChannel: PaymentChannel? = null
)

enum class FormError {
    INVALID_EMAIL,
    INVALID_PHONE,
    EMPTY_NAME
}

sealed class PaymentError (val code: String) : Throwable() {
    data class InvalidChannel(val channel: String?) : PaymentError("INVALID CHANNEL")
    class NetworkError : PaymentError("NETWORK ERROR")
    class Unknown : PaymentError("UNKNOWN ERROR")
    class GenericError : PaymentError("GENERIC ERROR")
}