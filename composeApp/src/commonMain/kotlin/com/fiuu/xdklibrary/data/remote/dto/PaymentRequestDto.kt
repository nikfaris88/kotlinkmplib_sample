package com.fiuu.xdklibrary.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequestDto(
    @SerialName("MerchantID")
    val mp_merchant_ID: String,
    @SerialName("mp_verification_key")
    val mp_verification_key: String,
    @SerialName("TxnType")
    val txnType: String,
    @SerialName("TxnCurrency")
    val txnCurrency: String,
    @SerialName("TxnAmount")
    val txnAmount: String,
    @SerialName("uat")
    val uat: Boolean,
    @SerialName("TxnChannel")
    val txnChannel: String,
    @SerialName("l_version")
    val l_version: String
)