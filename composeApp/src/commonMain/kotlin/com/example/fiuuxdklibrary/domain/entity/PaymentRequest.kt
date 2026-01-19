package com.example.fiuuxdklibrary.domain.entity

data class PaymentRequest(
    val order: OrderDetails,
    val amount: PaymentAmount,
    val method: PaymentChannel
)