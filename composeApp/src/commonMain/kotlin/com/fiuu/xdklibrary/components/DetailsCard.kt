package com.fiuu.xdklibrary.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import com.fiuu.xdklibrary.ui.model.PaymentUiState
import com.fiuu.xdklibrary.ui.theme.AppColors

@Composable
fun OrderDetailsCard(
    state: PaymentUiState,
    onName: (String) -> Unit,
    onEmail: (String) -> Unit,
    onPhone: (String) -> Unit,
    onDesc: (String) -> Unit
) {
    ExpandableCardPayment(
        label = "Order Details",
        initiallyExpanded = false,
        backgroundColor = AppColors.White,
        headerContent = {
            Column {
                CustomText(
                    label = "ID ${state.orderId}",
                )
            }
        },
        expandedContent = {
            TopLabelOutlinedTextField(
                value = state.name,
                onValueChange = onName,
                label = "Name",
                isError = state.formError != null,
                errorText = "Name cannot be empty",
                trailingIcon = Icons.Default.Person,
            )
            TopLabelOutlinedTextField(
                value = state.email,
                onValueChange = onEmail,
                label = "Email",
                isError = state.formError != null,
                errorText = "Invalid email address",
                trailingIcon = Icons.Default.Email,
            )
            TopLabelOutlinedTextField(
                value = state.mobile,
                onValueChange = onPhone,
                label = "Mobile",
                errorText = "Invalid phone number",
                trailingIcon = Icons.Default.Phone,
            )
            TopLabelOutlinedTextField(
                value = state.description,
                onValueChange = onDesc,
                label = "Description",
                trailingIcon = Icons.Default.Description,
            )
        }

    )
}