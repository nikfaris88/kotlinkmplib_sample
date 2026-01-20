package com.example.fiuuxdklibrary.sample

import FiuuPaymentSDK
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            val randomInt = (1..100).random()
            val paymentParams = mapOf<String, Any?>(
                "mp_amount" to "123.45",
                "mp_username" to "RMSxdk_2022",
                "mp_password" to "RMSpwd@2022",
                "mp_merchant_ID" to "rmsxdk_mobile_Dev",
                "mp_app_name" to "mobile",
                "mp_order_ID" to "$randomInt",
                "mp_currency" to "MYR",
                "mp_country" to "MY",
                "mp_verification_key" to "ee738b541eff7b6b495e44771f71c0ec",
                "mp_channel" to "TNG-EWALLET",
                "mp_bill_description" to "Purchase of item X",
                "mp_bill_name" to "John Doe",
                "mp_bill_email" to "john@example.com",
                "mp_bill_mobile" to "0123456789",
                "mp_sandbox_mode" to "true",
                "mp_dev_mode" to false,
                "mp_express_mode" to false,
                "device_info" to "Android 14, Pixel 7"
            )

            FiuuPaymentSDK(
                params = paymentParams,
                onPaymentResult = {
                    Toast.makeText(this@MainActivity, "Payment result received", Toast.LENGTH_SHORT)
                        .show()
                },
                onRequestClose = {
                    finish()
                }
            )

//            StartPaymentWebView(
//                params = paymentParams,
//                webViewBridge = AndroidWebViewBridge(this@MainActivity),
//                onPaymentResult = {
//                    Toast.makeText(this@MainActivity, "Payment result received", Toast.LENGTH_SHORT)
//                        .show()
//                },
//                onRequestClose = {
//                    finish()
//                }
//            )
        }
    }
}

