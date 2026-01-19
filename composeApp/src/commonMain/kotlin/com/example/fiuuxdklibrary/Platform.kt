package com.example.fiuuxdklibrary

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform