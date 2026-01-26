package com.example.fiuuxdklibrary.domain.entity

import com.example.fiuuxdklibrary.data.remote.dto.PaymentRequestDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentRequest(
    // General Parameters with mp_ mapping
    @SerialName("mp_amount") val mpAmount: String? = null,
    @SerialName("mp_username") val mpUsername: String? = null,
    @SerialName("mp_password") val mpPassword: String? = null,
    @SerialName("mp_merchant_ID") val mpMerchantID: String? = null,
    @SerialName("mp_app_name") val mpAppName: String? = null,
    @SerialName("mp_order_ID") val mpOrderID: String? = null,
    @SerialName("mp_currency") val mpCurrency: String? = null,
    @SerialName("mp_country") val mpCountry: String? = null,
    @SerialName("mp_verification_key") val mpVerificationKey: String? = null,
    @SerialName("mp_channel") val mpChannel: String? = null,
    @SerialName("mp_bill_description") val mpBillDescription: String? = null,
    @SerialName("mp_bill_name") val mpBillName: String? = null,
    @SerialName("mp_bill_email") val mpBillEmail: String? = null,
    @SerialName("mp_bill_mobile") val mpBillMobile: String? = null,

    // Boolean mapping (KMP handles these as true/false in JSON)
    @SerialName("mp_express_mode") val mpExpressMode: Boolean = false,
    @SerialName("mp_sandbox_mode") val mpSandboxMode: Boolean? = null,
    @SerialName("mp_dev_mode") val mpDevMode: Boolean = false,

    @SerialName("device_info") val deviceInfo: String? = null
)

fun Map<String, Any?>.toPaymentRequest(): PaymentRequest {
    return PaymentRequest(
        // Flat "mp_" parameters mapped to camelCase properties via SerialName logic
        mpAmount = this["mp_amount"]?.toString(),
        mpCurrency = this["mp_currency"]?.toString(),
        mpCountry = this["mp_country"]?.toString(),

        mpOrderID = this["mp_order_ID"]?.toString(),
        mpUsername = this["mp_username"]?.toString(),
        mpPassword = this["mp_password"]?.toString(),
        mpMerchantID = this["mp_merchant_ID"]?.toString(),
        mpAppName = this["mp_app_name"]?.toString(),
        mpVerificationKey = this["mp_verification_key"]?.toString(),

        mpChannel = this["mp_channel"]?.toString(),

        mpBillDescription = this["mp_bill_description"]?.toString(),
        mpBillName = this["mp_bill_name"]?.toString(),
        mpBillEmail = this["mp_bill_email"]?.toString(),
        mpBillMobile = this["mp_bill_mobile"]?.toString(),

        // Boolean mapping: bridges like Flutter often send true/false as native Booleans
        mpExpressMode = this["mp_express_mode"] as? Boolean ?: false,
        mpSandboxMode = this["mp_sandbox_mode"] as? Boolean ?: false,
        mpDevMode = this["mp_dev_mode"] as? Boolean ?: false,

        deviceInfo = this["device_info"]?.toString()
    )
}

fun PaymentRequest.toDto(
    txnType: String,
    uat: Boolean,
    lVersion: String
): PaymentRequestDto {
    return PaymentRequestDto(
        mp_merchant_ID = mpMerchantID
            ?: error("mpMerchantID is required"),

        mp_verification_key = mpVerificationKey
            ?: error("mpVerificationKey is required"),

        txnType = txnType,
        txnCurrency = mpCurrency
            ?: error("mpCurrency is required"),

        txnAmount = mpAmount
            ?: error("mpAmount is required"),

        uat = uat,

        txnChannel = mpChannel
            ?: error("mpChannel is required"),

        l_version = lVersion
    )
}