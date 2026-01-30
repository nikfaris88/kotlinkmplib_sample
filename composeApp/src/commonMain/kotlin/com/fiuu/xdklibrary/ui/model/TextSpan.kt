package com.fiuu.xdklibrary.ui.model

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

data class TextSpan(
    val text: String,
    val style: SpanStyle
)

fun buildDynamicAnnotatedString(
    spans: List<TextSpan>
): AnnotatedString {
    return buildAnnotatedString {
        spans.forEach { span ->
            withStyle(span.style) {
                append(span.text)
            }
        }
    }
}