package com.example.fiuuxdklibrary

import android.os.Bundle
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
import com.example.fiuuxdklibrary.ui.PaymentFlow
import com.example.fiuuxdklibrary.ui.model.PaymentScreen
import com.example.fiuuxdklibrary.viewmodel.PaymentViewModel
import io.kamel.image.config.LocalKamelConfig

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val kamelConfig = provideKamelConfig()

        setContent {
            CompositionLocalProvider(
                LocalKamelConfig provides kamelConfig
            ) {
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
                        finish()
                    }
                )
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