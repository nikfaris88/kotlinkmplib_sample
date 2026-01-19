package com.example.fiuuxdklibrary.ui.model

data class PaymentUiState(
    val amount: Long = 1000L,
    val orderId: String = "1759893168562",
    val name: String = "",
    val email: String = "",
    val mobile: String = "",
    val description: String = "",
    val loading: Boolean = false,
    val formError: FormError? = null,
    val paymentError: String? = null
)

enum class FormError {
    INVALID_EMAIL,
    INVALID_PHONE,
    EMPTY_NAME
}