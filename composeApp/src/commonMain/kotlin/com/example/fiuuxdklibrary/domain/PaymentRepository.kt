package com.example.fiuuxdklibrary.domain

import com.example.fiuuxdklibrary.data.remote.dto.PaymentRequestDto
import com.example.fiuuxdklibrary.data.remote.dto.PaymentResponseDto
import com.example.fiuuxdklibrary.domain.entity.PaymentRequest


interface PaymentRepository {
    suspend fun generatePayment(request: PaymentRequestDto): PaymentResponseDto
    suspend fun startPayment(request: PaymentRequest): Result<String>
}