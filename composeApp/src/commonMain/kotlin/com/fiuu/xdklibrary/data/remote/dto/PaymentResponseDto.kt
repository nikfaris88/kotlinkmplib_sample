package com.fiuu.xdklibrary.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentResponseDto(


    @SerialName("requestURL")
    val requestURL: String,
    @SerialName("requestMethod")
    val requestMethod: String,
    @SerialName("requestType")
    val requestType: String,
    @SerialName("referenceNo")
    val referenceNo: String,
    @SerialName("status")
    val status: Boolean,
    @SerialName("transactionId")
    val transactionId: String,
    @SerialName("message")
    val message: String
)