package com.example.fiuuxdklibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fiuuxdklibrary.data.repository.FiuuPaymentRepository
import com.example.fiuuxdklibrary.domain.entity.OrderDetails
import com.example.fiuuxdklibrary.domain.entity.PaymentAmount
import com.example.fiuuxdklibrary.domain.entity.PaymentChannel
import com.example.fiuuxdklibrary.domain.entity.PaymentRequest
import com.example.fiuuxdklibrary.domain.entity.toPaymentRequest
import com.example.fiuuxdklibrary.ui.model.FormError
import com.example.fiuuxdklibrary.ui.model.PaymentUiState
import com.example.fiuuxdklibrary.utils.ValidationUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val repo: FiuuPaymentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState = _uiState.asStateFlow()
    private var currentRequest: PaymentRequest? = null

    fun initialize(params: Map<String, Any?>) {
        val request = params.toPaymentRequest()
        this.currentRequest = request

        _uiState.update { it.copy(
            amount = request.mpAmount?.toDoubleOrNull()?.let { (it * 100).toLong() } ?: 0L,
            name = request.order.email,
            email = request.order.email,
            mobile = request.order.email,
            description = request.order.description
        )}
    }

    fun updateName(value: String) {
        _uiState.update { it.copy(
            name = value,
            formError = if (it.formError == FormError.EMPTY_NAME)
                null else it.formError
        ) }
    }

    fun updateEmail(value: String) {
        _uiState.update { it.copy(
            email = value,
            formError = if (it.formError == FormError.INVALID_EMAIL) null else it.formError
        ) }
    }

    fun updatePhone(value: String) {
        _uiState.update { it.copy(
            mobile = value,
            formError = if ( it.formError == FormError.INVALID_PHONE ) null else it.formError
        ) }
    }

    fun updateDescription(value: String) {
        _uiState.update { it.copy(description = value) }
    }

    fun startPayment(method: PaymentChannel) {
        val request = currentRequest ?: return

        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }

            val state = _uiState.value

            val finalRequest = request.copy(
                // Update user details in case they edited them in the SDK UI
                mpBillName = state.name,
                mpBillEmail = state.email,
                mpBillMobile = state.mobile,
                mpBillDescription = state.description,

                // Set the selected payment method
                mpChannel = method.id,

                // Keep the structured objects in sync
                amount = PaymentAmount(request.mpCurrency ?: "MYR", state.amount),
                order = OrderDetails(
                    orderId = state.orderId,
                    name = state.name,
                    email = state.email,
                    mobile = state.mobile,
                    description = state.description
                ),
                method = method
            )

            val result = repo.startPayment(finalRequest)

            _uiState.update {
                it.copy(
                    loading = false,
                    paymentError = result.exceptionOrNull()?.message
                )
            }

            if (result.isSuccess) {
                result.getOrNull()?.let { response -> println("RESPONSE: $response") }
            }
        }
    }
}