package com.fiuu.xdklibrary.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fiuu.xdklibrary.components.BottomPayBar
import com.fiuu.xdklibrary.components.CustomAlertDialog
import com.fiuu.xdklibrary.components.CustomText
import com.fiuu.xdklibrary.data.remote.dto.getMockPaymentChannels
import com.fiuu.xdklibrary.domain.entity.PaymentRequest
import com.fiuu.xdklibrary.ui.model.PaymentError
import com.fiuu.xdklibrary.ui.model.TextSpan
import com.fiuu.xdklibrary.ui.theme.AppColors
import com.fiuu.xdklibrary.viewmodel.PaymentViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SinglePaymentScreen(
    vm: PaymentViewModel,
    paymentRequest: PaymentRequest,
    cornerRadius: Dp = 12.dp,
    backgroundColor: Color = Color.White,
    onRequestClose: () -> Unit
) {

    val channels = getMockPaymentChannels()
    val uiState by vm.uiState.collectAsState()

    val selectedChannel = remember(paymentRequest.mpChannel) {
        channels.firstOrNull { it.name == paymentRequest.mpChannel || it.name == paymentRequest.mpChannel }
    }

    LaunchedEffect(selectedChannel) {
        if (selectedChannel == null) {
            vm.setPaymentError(
                PaymentError.InvalidChannel(paymentRequest.mpChannel)
            )
        }
    }

    // 🔴 ERROR DIALOG (belongs here)
    uiState.paymentError?.let { error ->
        val message = when (error) {
            is PaymentError.InvalidChannel ->
                "This payment channel is not supported."

            PaymentError.NetworkError() ->
                "Network error. Please check your internet connection."

            PaymentError.Unknown() ->
                "Unexpected error occurred."

            else -> {
                "Unexpected error occurred."
            }
        }

        CustomAlertDialog(
            title = "Payment Error",
            message = message,
            backgroundColor = AppColors.Primary,
            cornerRadius = 16.dp,
            confirmButtonColor = AppColors.Secondary,
            titleTextColor = AppColors.White,
            messageTextColor = AppColors.White,
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning, // or your custom icon
                    contentDescription = "Warning",
                    tint = Color(0xFFFFAA00), // orange color for warning
                    modifier = Modifier.size(48.dp)
                )
            },
            onDismiss = {
                vm.clearPaymentError()
                onRequestClose()
            }
        )
    }

    //STOP RENDERING UI IF PAYMENT CHANNEL NOT MEET
    if (selectedChannel == null) return

    Card (
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                "Selected Channel",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            if (selectedChannel.logoUrl3 != null) {
                KamelImage(
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(70.dp),
                    contentDescription = uiState.selectedChannel?.title,
                    onLoading = { progress ->
                        CircularProgressIndicator(modifier = Modifier.size(16.dp))
                    },
                    onFailure = { throwable ->
                        Icon(Icons.Default.BrokenImage, contentDescription = null)
                    },
                    resource = {
                        asyncPainterResource(selectedChannel.logoUrl3)
                    }
                )

                Text(
                    selectedChannel.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.size(16.dp))

                CustomText(
                    textAlign = TextAlign.Center,
                    spans = listOf(
                        TextSpan(
                            text = "By continuing you have read and agree to\n",
                            style = SpanStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        ),
                        TextSpan(
                            text = "Terms & Conditions",
                            style = SpanStyle(
                                fontSize = 16.sp,
                                color = AppColors.Primary,
                                fontWeight = FontWeight.Normal
                            )
                        ),
                        TextSpan(
                            text = " & ",
                            style = SpanStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            )
                        ),
                        TextSpan(
                            text = "Privacy Policy",
                            style = SpanStyle(
                                fontSize = 16.sp,
                                color = AppColors.Primary,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    ),
                )

                Spacer(Modifier.size(8.dp))


                BottomPayBar(
                    enabled = true,
                    amount = vm.uiState.collectAsState().value.amount,
                    currency = vm.uiState.collectAsState().value.currency,
                    onPay = { vm.startPayment() },
                )
            }
        }


    }
}