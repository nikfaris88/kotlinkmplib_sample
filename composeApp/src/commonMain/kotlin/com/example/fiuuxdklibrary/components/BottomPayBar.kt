package com.example.fiuuxdklibrary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fiuuxdklibrary.ui.theme.AppColors
import com.example.fiuuxdklibrary.utils.NumberFormatter

@Composable
fun BottomPayBar(
    enabled: Boolean,
    amount: Long,
    currency: String,
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
            colors = ButtonColors(
                containerColor = AppColors.Secondary,
                disabledContainerColor = AppColors.TextSecondary,
                contentColor = AppColors.White,
                disabledContentColor = AppColors.TextPrimary
            ),
            onClick = onPay,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Pay ($currency ${NumberFormatter.formatAmount(amount.toDouble())})")
        }
    }
}