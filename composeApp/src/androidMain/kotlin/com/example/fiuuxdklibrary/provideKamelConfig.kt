package com.example.fiuuxdklibrary

import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpUrlFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.ktor.client.HttpClient

actual fun provideKamelConfig(): KamelConfig =
    KamelConfig {
        takeFrom(KamelConfig.Default)

        httpUrlFetcher {
            HttpClient()
        }
    }