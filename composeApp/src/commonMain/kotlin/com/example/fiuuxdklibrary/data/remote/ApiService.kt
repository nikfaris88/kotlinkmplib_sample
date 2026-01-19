package com.example.fiuuxdklibrary.data.remote

import com.example.fiuuxdklibrary.data.remote.dto.PaymentRequestDto
import com.example.fiuuxdklibrary.data.remote.dto.PaymentResponseDto

interface ApiService {
    suspend fun createPayment(
        request: PaymentRequestDto
    ): PaymentResponseDto
}