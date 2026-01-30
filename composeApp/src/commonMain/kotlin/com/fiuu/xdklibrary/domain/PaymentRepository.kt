package com.fiuu.xdklibrary.domain

import com.fiuu.xdklibrary.data.remote.dto.PaymentRequestDto
import com.fiuu.xdklibrary.data.remote.dto.PaymentResponseDto
import com.fiuu.xdklibrary.domain.entity.PaymentRequest


interface PaymentRepository {
    suspend fun generatePayment(request: PaymentRequestDto): PaymentResponseDto
    suspend fun startPayment(request: PaymentRequest): Result<String>
}