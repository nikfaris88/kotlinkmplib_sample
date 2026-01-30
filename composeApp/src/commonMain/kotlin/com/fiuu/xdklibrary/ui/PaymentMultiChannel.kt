package com.fiuu.xdklibrary.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fiuu.xdklibrary.components.AmountCard
import com.fiuu.xdklibrary.components.CustomText
import com.fiuu.xdklibrary.components.OrderDetailsCard
import com.fiuu.xdklibrary.components.PaymentMethods
import com.fiuu.xdklibrary.components.PaymentScaffold
import com.fiuu.xdklibrary.components.QuickPay
import com.fiuu.xdklibrary.data.remote.dto.getMockPaymentChannels
import com.fiuu.xdklibrary.domain.entity.PaymentRequest
import com.fiuu.xdklibrary.ui.model.PayWithItem
import com.fiuu.xdklibrary.ui.model.PaymentScreen
import com.fiuu.xdklibrary.viewmodel.PaymentViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PaymentMultiChannel(
    vm: PaymentViewModel,
    paymentRequest: PaymentRequest,
    screen: PaymentScreen,
    onScreenChange: (PaymentScreen) -> Unit,
    showAppBar: Boolean = true,
    onRequestClose: () -> Unit,
     ) {
    val state by vm.uiState.collectAsState()

    // MOCK as multi channel. show all available channel
    val channels = getMockPaymentChannels()
    val eWalletChannels = channels.filter { it.channelType == "EW"}
    val googlePayChannel = channels.firstOrNull { it.name.equals("GooglePay", ignoreCase = true) }
    val fpxChannels = channels.filter { it.channelType == "IB" }
    val ccChannels = channels.filter { it.channelType == "CC" }
    val bnplChannels = channels.filter { it.channelType == "BNPL" }
    val cashChannels = channels.filter { it.channelType == "OTC" }
    val pluginChannels = channels.filter { it.channelType == "PLUGIN" }

    PaymentScaffold(
        showAppBar = showAppBar,
        title = "Fiuu Payment",
        onBack = {
            when (screen) {
                PaymentScreen.Methods -> onRequestClose()
                is PaymentScreen.Channels -> onScreenChange(PaymentScreen.Methods)
            }
        }
    ) {

        when (screen) {
            PaymentScreen.Methods -> {
                Box {
                    LazyColumn (
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFF4F6F8))
                            .padding(16.dp),
                        contentPadding = PaddingValues(bottom = 56.dp)
                    ) {
                        item {
                            Column {
                                AmountCard(state.amount)

                                Spacer(modifier = Modifier.padding(top = 8.dp))

                                //Getting user info from paymentrequest
                                OrderDetailsCard(
                                    state = state,
                                    onName = vm::updateName,
                                    onEmail = vm::updateEmail,
                                    onPhone = vm::updatePhone,
                                    onDesc = vm::updateDescription
                                )

                                Spacer(modifier = Modifier.padding(top = 8.dp))

                                when {
                                    paymentRequest.mpChannel?.trim()?.lowercase() == "multi" ->{
                                        Column {
                                            //THIS PART BELOW WILL UPDATE USER SELECTION CHANNEL
                                            if (googlePayChannel != null) {
                                                QuickPay (
                                                    items= pluginChannels.filter { channel ->
                                                        channel.maskName == "GooglePay" &&
                                                                channels.isNotEmpty()
                                                    }
                                                ){
                                                    //DO QUICK PAY USING GOOGLE PAY
                                                }
                                                Spacer(modifier = Modifier.padding(top = 8.dp))
                                            }

                                            //SELECTION CHANNEL
                                            PaymentMethods(
                                                items = listOf(
                                                    PayWithItem(
                                                        title = "Credit / Debit Card",
                                                        channels = ccChannels,
                                                    ),
                                                    PayWithItem(
                                                        title = "Online Banking",
                                                        channels = fpxChannels,
                                                    ),
                                                    PayWithItem(
                                                        title = "eWallets",
                                                        channels = eWalletChannels
                                                    ),
                                                    PayWithItem(
                                                        title = "Buy Now Pay Later",
                                                        channels = bnplChannels
                                                    ),
                                                    PayWithItem(
                                                        title = "Fiuu CASH",
                                                        channels = cashChannels
                                                    ),
                                                ).filter { it.channels.isNotEmpty() }
                                            ) {
                                                    item -> onScreenChange( PaymentScreen.Channels(item))
                                            }
                                        }
                                    } else -> {
                                        if (paymentRequest.mpChannel?.trim()?.lowercase()?.contains("credit") == true) {
                                            CheckoutScreen()
//                                            CardPaymentScreen(
//                                                vm = vm,
//                                                paymentRequest,
//                                                onRequestClose = onRequestClose
//                                            )
                                        }else {
                                            SinglePaymentScreen(
                                                vm = vm,
                                                paymentRequest,
                                                onRequestClose = onRequestClose
                                            )
                                        }

                                    }
                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomText(
                            label = "Version 5.0.0",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }

            is PaymentScreen.Channels -> {
                PaymentChannelSelection(
                    vm = vm,
                    item = screen.item,
                )
            }

        }
    }
}