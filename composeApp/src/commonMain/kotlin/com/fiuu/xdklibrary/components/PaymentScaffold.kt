package com.fiuu.xdklibrary.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fiuu.xdklibrary.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScaffold(
    showAppBar: Boolean = true,
    title: String = "Payment",
    onBack: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Scaffold(
        Modifier.background(AppColors.White),
        topBar = {
            if (showAppBar) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = AppColors.Primary,
                        titleContentColor = AppColors.White,
                        navigationIconContentColor = AppColors.White
                    ),
                    title = { Text(title) },
                    navigationIcon = {
                        if (onBack != null) {
                            Text(
                                "←",
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .clickable { onBack() }
                            )
                        }
                    }
                )
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            content()
        }
    }
}
