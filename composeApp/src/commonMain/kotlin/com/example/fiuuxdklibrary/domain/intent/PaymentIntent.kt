package com.example.fiuuxdklibrary.domain.intent

sealed class PaymentIntent {
    data class AmountChanged(val amount: String) : PaymentIntent()
    object PayClicked : PaymentIntent()
}