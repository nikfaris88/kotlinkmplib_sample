package com.example.fiuuxdklibrary.data.repository

import com.example.fiuuxdklibrary.domain.PaymentRepository
import com.example.fiuuxdklibrary.domain.entity.PaymentRequest

class FiuuPaymentRepository(

) : PaymentRepository {
    override suspend fun startPayment(request: PaymentRequest): Result<String> {
        return try {
//            tapSdk.startPayment(request)
            Result.success("STARTED")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}