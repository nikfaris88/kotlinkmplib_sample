package com.fiuu.xdklibrary.data.remote

import com.fiuu.xdklibrary.data.remote.dto.PaymentRequestDto
import com.fiuu.xdklibrary.data.remote.dto.PaymentResponseDto


interface ApiService {
    suspend fun generatePayment(
        request: PaymentRequestDto
    ): PaymentResponseDto
}