package com.fiuu.xdklibrary.data.mapper

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Payments
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.fiuu.xdklibrary.domain.entity.ChannelType

@Composable
fun ChannelType.toIcon(): ImageVector {
    return when (this){
        ChannelType.GOOGLE_PAY -> Icons.Default.AccountBalanceWallet
        ChannelType.CARD -> Icons.Default.CreditCard
        ChannelType.INSTALLMENT -> Icons.Default.DateRange
        ChannelType.FPX -> Icons.Default.AccountBalance
        ChannelType.EWALLET -> Icons.Default.Payments
        ChannelType.FIUU_CASH -> Icons.Default.Money
    }
}