package com.example.fiuuxdklibrary.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fiuuxdklibrary.components.BottomPayBar
import com.example.fiuuxdklibrary.components.ChannelGrid
import com.example.fiuuxdklibrary.ui.model.PayWithItem
import com.example.fiuuxdklibrary.viewmodel.PaymentViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PaymentChannelSelection(
    vm: PaymentViewModel,
    item: PayWithItem
) {
    val uiState by vm.uiState.collectAsState()

    Box(Modifier.fillMaxSize()) {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFFF4F6F8))
                .padding(16.dp)
        ) {

            Text(
                "Please Select A Channel To Proceed",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(Modifier.height(12.dp))

            ChannelGrid(
                channels = item.channels,
                selected = uiState.selectedChannel,
                onSelect = { vm.onChannelSelected(it)}
            )
        }

        BottomPayBar(
            enabled = uiState.selectedChannel != null,
            amount = vm.uiState.collectAsState().value.amount,
            currency = vm.uiState.collectAsState().value.currency,
            onPay = { vm.startPayment() },
            modifier = Modifier.align(Alignment.BottomCenter)
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "By continuing you have read and agree to the Terms & Conditions & Privacy Policy.",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}




