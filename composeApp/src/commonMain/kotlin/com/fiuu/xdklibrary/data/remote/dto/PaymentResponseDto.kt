package com.fiuu.xdklibrary.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentResponseDto(
    @SerialName("RequestURL")
    val requestURL: String,
    @SerialName("RequestMethod")
    val requestMethod: String,
    @SerialName("RequestType")
    val requestType: String,
    @SerialName("ref")
    val ref: String,
    @SerialName("status")
    val status: Boolean
)