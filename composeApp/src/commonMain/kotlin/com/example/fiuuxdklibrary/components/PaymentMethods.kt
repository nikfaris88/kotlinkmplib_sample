package com.example.fiuuxdklibrary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.fiuuxdklibrary.ui.model.PayWithItem
import com.example.fiuuxdklibrary.ui.theme.AppColors

@Composable
fun PaymentMethods(
    items: List<PayWithItem>,
    onItemClick: (PayWithItem) -> Unit
) {
    PaymentCard(
        label = "Pay With",
        layout = PaymentCardLayout.VERTICAL
    ) {
        Column(
            modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(AppColors.White),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                PaymentMethodRow(item,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }

}
