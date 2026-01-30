package com.fiuu.xdklibrary.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fiuu.xdklibrary.platform.C2PPaymentHandler
import com.fiuu.xdklibrary.ui.theme.AppColors


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CheckoutScreen(
) {
   Card (
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = AppColors.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "Selected Channel",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            var message by remember { mutableStateOf("") }

            val configJson = """{
                "dpaId": "82d6652a-60c6-4f2d-9c8a-47b7ff592321",
                "locale": "en_MY"
            }"""

                    val detailsJson = """{
                "amount": "10.00",
                "currency": "MYR"
            }"""

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                C2PPaymentHandler().PaymentScreen(configJson, detailsJson) { result ->
                    result.fold(
                        onSuccess = { token -> message = "Payment Success: $token" },
                        onFailure = { err -> message = "Payment Failed: ${err.message}" }
                    )
                }

                if (message.isNotEmpty()) {
                    Text(message)
                }
            }
        }


    }
}