package com.example.fiuuxdklibrary.data.repository

import com.example.fiuuxdklibrary.domain.PaymentRepository
import com.example.fiuuxdklibrary.domain.entity.PaymentRequest
import kotlinx.serialization.json.Json

class FiuuPaymentRepository(

) : PaymentRepository {
    override suspend fun startPayment(request: PaymentRequest): Result<String> {
        return try {
            println("Payment REQUEST: ${Json.encodeToString(request)}")
//            tapSdk.startPayment(request)
            Result.success("STARTED")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}