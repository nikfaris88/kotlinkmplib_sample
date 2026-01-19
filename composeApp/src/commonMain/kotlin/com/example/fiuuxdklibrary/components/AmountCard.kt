package com.example.fiuuxdklibrary.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.fiuuxdklibrary.ui.model.TextSpan

@Composable
fun AmountCard(amount: Long) {
    PaymentCard(
        "Amount",
        ) {
        CustomText(
            spans = listOf(
                TextSpan(
                    text = "MYR ",
                    style = SpanStyle(fontSize = 10.sp)
                ),
                TextSpan(
                    text = "1000.10",
                    style = SpanStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            ),
        )
    }
}