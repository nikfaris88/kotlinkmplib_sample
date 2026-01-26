package com.example.fiuuxdklibrary.data.remote

import com.example.fiuuxdklibrary.data.remote.dto.PaymentRequestDto
import com.example.fiuuxdklibrary.data.remote.dto.PaymentResponseDto
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json

class KtorApiService(
    httpClientFactory: HttpClientFactory
) : ApiService {

    private val httpClient = httpClientFactory.create()

    override suspend fun generatePayment(request: PaymentRequestDto): PaymentResponseDto {
        return httpClient.post("/generate-payment") {
            contentType(ContentType.Application.Json)
            setBody(Json.encodeToString(request))
        }.body()
    }
}