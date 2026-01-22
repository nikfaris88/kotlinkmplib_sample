package com.example.fiuuxdklibrary.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fiuuxdklibrary.domain.entity.PaymentChannel
import com.example.fiuuxdklibrary.ui.theme.AppColors
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource


//PAYMENT CHANNEL SELECTION
@Composable
fun ChannelGrid(
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
        colors = CardDefaults.cardColors(containerColor = if (selected) AppColors.TextHint else AppColors.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (channel.logoUrl3 != null) {
                KamelImage(
                    contentScale = ContentScale.Fit,
                    resource = { asyncPainterResource(channel.logoUrl3) },
                    modifier = Modifier.size(60.dp),
                    contentDescription = channel.title,
                    onLoading = {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp))
                    },
                    onFailure = {
                        Icon(Icons.Default.BrokenImage, contentDescription = null)
                    }
                )
            }

            Spacer(modifier = Modifier.size(4.dp))
            Text(
                channel.title,
                style = MaterialTheme.typography.labelMedium,
                lineHeight = 20.sp,
                maxLines = 2,
                overflow = TextOverflow.Clip,
            )
        }
    }
}