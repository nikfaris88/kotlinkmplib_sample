package com.example.fiuuxdklibrary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fiuuxdklibrary.data.mapper.PaymentChannelImage
import com.example.fiuuxdklibrary.domain.entity.PaymentChannel
import com.example.fiuuxdklibrary.ui.theme.AppColors

@Composable
fun QuickPay(
    items: List<PaymentChannel>,
    onClick: () -> Unit
) {
    PaymentCard(
        layout = PaymentCardLayout.VERTICAL,
        label = "Quick Pay",
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)        ) {
            items.forEach { item ->
                PaymentCard(
                    label = item.title,
                    channelIcon = {
                        PaymentChannelImage(
                            title = item.name,
                            contentDescription = item.maskName,
                        )
//                    Icon(
//                        imageVector = Icons.Default.Payment,
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.onSurface
//                    )
                    },
                    rightContent = {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = null
                        )
                    }
                )

            }
        }

    }
}
