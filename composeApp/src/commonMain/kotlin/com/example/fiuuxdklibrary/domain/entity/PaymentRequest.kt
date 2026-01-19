package com.example.fiuuxdklibrary.domain.entity

import kotlinx.serialization.SerialName

data class PaymentRequest(
    val order: OrderDetails,
    val amount: PaymentAmount,
    val method: PaymentChannel,

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
    @SerialName("mp_sandbox_mode") val mpSandboxMode: String? = null,
    @SerialName("mp_dev_mode") val mpDevMode: Boolean = false,

    @SerialName("device_info") val deviceInfo: String? = null
)

fun Map<String, Any?>.toPaymentRequest(): PaymentRequest {
    return PaymentRequest(
        // Internal structured data
        order = OrderDetails(
            orderId = this["mp_order_ID"]?.toString() ?: "",
            name = this["mp_bill_name"]?.toString() ?: "",
            email = this["mp_bill_email"]?.toString() ?: "",
            mobile = this["mp_bill_mobile"]?.toString() ?: "",
            description = this["mp_bill_description"]?.toString() ?: ""
        ),
        amount = PaymentAmount(
            currency = this["mp_currency"]?.toString() ?: "MYR",
            // Safely parse amounts; handle cases where amount might be String or Number
            amount = this["mp_amount"]?.toString()?.toDoubleOrNull()?.toLong() ?: 0L
        ),
        method = PaymentChannel(name = "Default", title = "Default", status = 1),

        // Flat "mp_" parameters mapped to camelCase properties via SerialName logic
        mpAmount = this["mp_amount"]?.toString(),
        mpUsername = this["mp_username"]?.toString(),
        mpPassword = this["mp_password"]?.toString(),
        mpMerchantID = this["mp_merchant_ID"]?.toString(),
        mpAppName = this["mp_app_name"]?.toString(),
        mpOrderID = this["mp_order_ID"]?.toString(),
        mpCurrency = this["mp_currency"]?.toString(),
        mpCountry = this["mp_country"]?.toString(),
        mpVerificationKey = this["mp_verification_key"]?.toString(),
        mpChannel = this["mp_channel"]?.toString(),
        mpBillDescription = this["mp_bill_description"]?.toString(),
        mpBillName = this["mp_bill_name"]?.toString(),
        mpBillEmail = this["mp_bill_email"]?.toString(),
        mpBillMobile = this["mp_bill_mobile"]?.toString(),

        // Boolean mapping: bridges like Flutter often send true/false as native Booleans
        mpSandboxMode = this["mp_sandbox_mode"]?.toString(),
        mpDevMode = this["mp_dev_mode"] as? Boolean ?: false,

        deviceInfo = this["device_info"]?.toString()
    )
}