package com.example.fiuuxdklibrary.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.fiuuxdklibrary.components.PaymentScaffold
import com.example.fiuuxdklibrary.domain.entity.PaymentChannel
import com.example.fiuuxdklibrary.ui.model.PayWithItem
import com.example.fiuuxdklibrary.ui.theme.AppColors
import com.example.fiuuxdklibrary.viewmodel.PaymentViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PaymentScreen(
    vm: PaymentViewModel,
    item: PayWithItem
) {
    var selected by remember { mutableStateOf<PaymentChannel?>(null) }

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
            selected = selected,
            onSelect = { selected = it }
        )
    }

    BottomPayBar(
        enabled = selected != null,
        amount = vm.uiState.collectAsState().value.amount,
        onPay = {
            selected?.let { vm.pay(it) }
        },
        modifier = Modifier.align(Alignment.BottomCenter)
    )
}
}

@Composable
private fun ChannelGrid(
    channels: List<PaymentChannel>,
    selected: PaymentChannel?,
    onSelect: (PaymentChannel) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(bottom = 120.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(channels) { channel ->
            ChannelGridItem(
                channel = channel,
                selected = channel == selected,
                onClick = { onSelect(channel) }
            )
        }
    }
}

@Composable
fun ChannelGridItem(
    channel: PaymentChannel,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            1.dp,
            if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.outline
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (channel.logoUrl3 != null) {
                KamelImage(
                    contentScale = ContentScale.Fit,
                    resource = asyncPainterResource(channel.logoUrl3),
                    modifier = Modifier.size(40.dp),
                    contentDescription = channel.title,
                    onLoading = {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp))
                    },
                    onFailure = {
                        Icon(Icons.Default.BrokenImage, contentDescription = null)
                    }
                )
            }

            Text(
                channel.title,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 2
            )
        }
    }
}

@Composable
fun BottomPayBar(
    enabled: Boolean,
    amount: Long,
    onPay: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Button(
            colors = ButtonColors(containerColor = AppColors.Secondary, disabledContainerColor = AppColors.TextSecondary, contentColor = AppColors.White, disabledContentColor = AppColors.TextPrimary),
            onClick = onPay,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Pay ($amount)")
        }

        Spacer(Modifier.height(8.dp))

        Text(
            "By continuing you have read and agree to the Terms & Conditions & Privacy Policy.",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

