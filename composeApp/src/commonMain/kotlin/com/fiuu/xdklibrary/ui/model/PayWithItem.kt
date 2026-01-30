package com.fiuu.xdklibrary.ui.model

import com.fiuu.xdklibrary.domain.entity.PaymentChannel

data class PayWithItem(
    val title : String,
    val channels: List<PaymentChannel>,
)