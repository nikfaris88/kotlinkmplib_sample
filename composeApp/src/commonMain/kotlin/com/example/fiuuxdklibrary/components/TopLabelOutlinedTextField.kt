package com.example.fiuuxdklibrary.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TopLabelOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorText: String? = null,
    trailingIcon: ImageVector,
) {
    Column(modifier) {
        CustomText(
            label = label,
        )

        Spacer(Modifier.height(4.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = null,
            modifier = Modifier.fillMaxWidth().height(45.dp),
            singleLine = true,
            shape = RoundedCornerShape(corner = CornerSize(12.dp)),
            isError = isError,
            trailingIcon = {
                Icon(imageVector = trailingIcon, contentDescription = null)
            },

        )
        if (isError && errorText != null) {
            CustomText(
                errorText = errorText,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}