package com.example.fiuuxdklibrary.ui.model

sealed class PaymentScreen {
    object Methods : PaymentScreen()
    data class Channels(val item: PayWithItem) : PaymentScreen()
}