package com.example.fiuuxdklibrary.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fiuuxdklibrary.ui.model.TextSpan
import com.example.fiuuxdklibrary.utils.NumberFormatter

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