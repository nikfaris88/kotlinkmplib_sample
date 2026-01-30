package com.fiuu.xdklibrary.platform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform