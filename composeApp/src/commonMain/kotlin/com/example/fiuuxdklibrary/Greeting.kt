package com.example.fiuuxdklibrary

import com.example.fiuuxdklibrary.platform.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}