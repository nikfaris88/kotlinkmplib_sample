package com.fiuu.xdklibrary.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fiuu.xdklibrary.ui.model.TextSpan
import com.fiuu.xdklibrary.utils.NumberFormatter

@Composable
fun AmountCard(amount: Long) {
    PaymentCard(
        padding = PaddingValues(start = 8.dp, end = 24.dp, top = 16.dp, bottom = 16.dp),
        label = "Amount",
        ) {
        CustomText(
            textAlign = TextAlign.Center,
            spans = listOf(
                TextSpan(
                    text = "MYR ",
                    style = SpanStyle(fontSize = 14.sp)
                ),
                TextSpan(
                    text = NumberFormatter.formatAmount(amount.toDouble()),
                    style = SpanStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            ),
        )
    }
}