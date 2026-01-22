package com.example.fiuuxdklibrary.ui.model

import com.example.fiuuxdklibrary.data.remote.dto.PaymentResponseDto
import com.example.fiuuxdklibrary.domain.entity.PaymentChannel

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
    object NetworkError : PaymentError("NETWORK ERROR")
    object Unknown : PaymentError("UNKNOWN ERROR")
}