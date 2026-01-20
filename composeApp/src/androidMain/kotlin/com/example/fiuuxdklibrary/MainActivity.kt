package com.example.fiuuxdklibrary

import StartPaymentWebView
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.fiuuxdklibrary.data.repository.FiuuPaymentRepository
import com.example.fiuuxdklibrary.platform.AndroidWebViewBridge
import com.example.fiuuxdklibrary.platform.provideKamelConfig
import com.example.fiuuxdklibrary.ui.PaymentFlow
import com.example.fiuuxdklibrary.ui.model.PaymentScreen
import com.example.fiuuxdklibrary.viewmodel.PaymentViewModel
import io.kamel.image.config.LocalKamelConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val kamelConfig = provideKamelConfig()

//        "mp_username": "RMSxdk_2022",
//        "mp_password": "RMSpwd@2022",
//        "mp_merchant_ID": "rmsxdk_mobile_Dev",
//        "mp_app_name": "mobile",
//        "mp_verification_key": 'ee738b541eff7b6b495e44771f71c0ec',
//// TODO: Enter mandatory String
//        "mp_order_ID": orderId, // Unique order id
//        "mp_currency": "MYR",
//        "mp_country": "MY",
//        "mp_amount": "0.10", // Minimum 1.01 must be in 2 decimal points format
//        "mp_channel": 'TNG-EWALLET', // TNG-EWALLET multi = all channels
//        "mp_bill_description": "testing",
//        "mp_bill_name": "asd",
//        "mp_bill_email": "nikfaris88@gmail.com",
//        "mp_bill_mobile": "60+60182233991",
//        "mp_express_mode": true,
//        "mp_closebutton_display": true,

        setContent {
            CompositionLocalProvider(
                LocalKamelConfig provides kamelConfig
            ) {
//                var screen by remember {
//                    mutableStateOf<PaymentScreen>(PaymentScreen.Methods)
//                }
//
//                val vm = remember {
//                    PaymentViewModel(FiuuPaymentRepository())
//                }

                val randomInt = (1..100).random()
                // Prepare parameters (could come from API, Flutter, or intent)
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
                    "mp_channel" to "TNG-EWALLET", // channel not selected yet
                    "mp_bill_description" to "Purchase of item X",
                    "mp_bill_name" to "John Doe",
                    "mp_bill_email" to "john@example.com",
                    "mp_bill_mobile" to "0123456789",
                    "mp_sandbox_mode" to "true",
                    "mp_dev_mode" to false,
                    "mp_express_mode" to false,
                    "device_info" to "Android 14, Pixel 7"
                )


                StartPaymentWebView(
                    params = paymentParams,
                    webViewBridge = AndroidWebViewBridge(this),
                    onPaymentResult = { response ->
                        Toast.makeText(this, "OPENINGGG", Toast.LENGTH_SHORT).show()

                    },
                    onRequestClose = {
                        finish()
                    }
                )

//                FiuuPaymentSDK(
//                    params = paymentParams,
//                    onPaymentResult = { response ->
//                        // Handle payment result here
//                        Toast.makeText(this, "Payment Success: ${response}", Toast.LENGTH_SHORT).show()
//                    },
//                    onRequestClose = {
//                        // Close payment flow
//                        finish()
//                    }
//                )

//                vm.initialize(paymentParams)
//
//                PaymentFlow(
//                    vm = vm,
//                    screen = screen,
//                    showAppBar = true,
//                    onScreenChange = { screen = it },
//                    onRequestClose = {
//                        finish()
//                    }
//                )
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    var screen by remember {
        mutableStateOf<PaymentScreen>(PaymentScreen.Methods)
    }

    val vm = remember {
        PaymentViewModel(FiuuPaymentRepository())
    }

    PaymentFlow(
        vm = vm,
        screen = screen,
        showAppBar = true,
        onScreenChange = { screen = it },
        onRequestClose = {
        }
    )
}