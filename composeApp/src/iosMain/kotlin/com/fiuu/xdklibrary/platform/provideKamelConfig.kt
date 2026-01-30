package com.fiuu.xdklibrary.platform

import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpUrlFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun provideKamelConfig(): KamelConfig =
    KamelConfig {
        takeFrom(KamelConfig.Default)

        httpUrlFetcher {
            HttpClient(Darwin)
        }
    }