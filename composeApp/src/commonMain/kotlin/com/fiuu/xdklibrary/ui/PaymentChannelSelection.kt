package com.fiuu.xdklibrary.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fiuu.xdklibrary.components.BottomPayBar
import com.fiuu.xdklibrary.components.ChannelGrid
import com.fiuu.xdklibrary.ui.model.PayWithItem
import com.fiuu.xdklibrary.viewmodel.PaymentViewModel

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




