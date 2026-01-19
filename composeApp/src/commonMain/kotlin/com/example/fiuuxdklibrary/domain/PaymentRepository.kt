package com.example.fiuuxdklibrary.domain

import com.example.fiuuxdklibrary.domain.entity.PaymentRequest


interface PaymentRepository {
    suspend fun startPayment(request: PaymentRequest): Result<String>
}