package com.example.fiuuxdklibrary.platform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform