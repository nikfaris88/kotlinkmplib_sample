package com.fiuu.xdklibrary.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fiuu.xdklibrary.data.mapper.PaymentChannelImage
import com.fiuu.xdklibrary.domain.entity.PaymentChannel

@Composable
fun QuickPay(
    items: List<PaymentChannel>,
    onClick: () -> Unit
) {
    PaymentCard(
        padding = PaddingValues(start = 8.dp, end = 24.dp, top = 16.dp, bottom = 16.dp),
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
