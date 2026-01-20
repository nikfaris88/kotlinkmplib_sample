package com.example.fiuuxdklibrary.utils

object NumberFormatter {

    fun formatAmount(amount: Double): String {
        val rounded = amount.toInt() / 100.0
        return rounded.toString().let {
            if (it.contains(".")) {
                val parts = it.split(".")
                parts[0] + "." + parts[1].padEnd(2, '0').take(2)
            } else {
                "$it.00"
            }
        }
    }
}
