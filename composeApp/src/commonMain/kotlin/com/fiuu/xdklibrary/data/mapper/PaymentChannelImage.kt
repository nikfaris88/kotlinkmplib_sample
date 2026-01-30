package com.fiuu.xdklibrary.data.mapper

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.fiuu.xdklibrary.generated.resources.Res
import com.fiuu.xdklibrary.generated.resources.apple_logo
import com.fiuu.xdklibrary.generated.resources.gpay_logo_white
import org.jetbrains.compose.resources.painterResource

@Composable
fun PaymentChannelImage(
    title: String,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    val painter = when (title.lowercase()) {
        "googlepay", "google pay", "gpay" ->
            painterResource(Res.drawable.gpay_logo_white)

        "applepay", "apple pay" ->
            painterResource(Res.drawable.apple_logo)

        else ->
            painterResource(Res.drawable.gpay_logo_white) // fallback
    }

    Image(
        colorFilter = if (title.lowercase() == "applepay") {
            ColorFilter.tint(Color.Black)
        } else {
            null
        },
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.size(40.dp)
    )
}