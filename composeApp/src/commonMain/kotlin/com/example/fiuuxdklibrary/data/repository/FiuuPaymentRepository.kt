package com.example.fiuuxdklibrary.data.repository

import com.example.fiuuxdklibrary.data.remote.KtorApiService
import com.example.fiuuxdklibrary.data.remote.dto.PaymentRequestDto
import com.example.fiuuxdklibrary.data.remote.dto.PaymentResponseDto
import com.example.fiuuxdklibrary.domain.PaymentRepository
import com.example.fiuuxdklibrary.domain.entity.PaymentRequest
import kotlinx.serialization.json.Json

class FiuuPaymentRepository(
    private val api: KtorApiService
) : PaymentRepository {

    override suspend fun generatePayment(request: PaymentRequestDto): PaymentResponseDto {
        println("Payment REQUEST: ${Json.encodeToString(request)}")
        return api.generatePayment(request)
    }
    override suspend fun startPayment(request: PaymentRequest): Result<String> {
        return try {
//            tapSdk.startPayment(request)
            Result.success("STARTED")
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}