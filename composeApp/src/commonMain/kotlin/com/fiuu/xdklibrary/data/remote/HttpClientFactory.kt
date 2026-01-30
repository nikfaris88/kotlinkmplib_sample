package com.fiuu.xdklibrary.data.remote

import io.ktor.client.HttpClient

expect class HttpClientFactory() {
    fun create(): HttpClient
}