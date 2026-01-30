package com.fiuu.xdklibrary.data.repository

import com.fiuu.xdklibrary.data.remote.KtorApiService
import com.fiuu.xdklibrary.data.remote.dto.PaymentRequestDto
import com.fiuu.xdklibrary.data.remote.dto.PaymentResponseDto
import com.fiuu.xdklibrary.domain.PaymentRepository
import com.fiuu.xdklibrary.domain.entity.PaymentRequest
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