package com.example.fiuuxdklibrary.ui.model

import com.example.fiuuxdklibrary.domain.entity.PaymentChannel

data class PayWithItem(
    val title : String,
    val channels: List<PaymentChannel>,
)