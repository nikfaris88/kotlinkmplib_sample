package com.example.fiuuxdklibrary.data.remote

import com.example.fiuuxdklibrary.data.remote.dto.PaymentRequestDto
import com.example.fiuuxdklibrary.data.remote.dto.PaymentResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class KtorApiService(
    private val httpClient: HttpClient
) : ApiService {

    override suspend fun createPayment(
        request: PaymentRequestDto
    ): PaymentResponseDto {
        return httpClient.post("payments") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()    }
}