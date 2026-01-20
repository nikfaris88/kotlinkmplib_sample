import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    // OR if using Kotlin DSL:
    kotlin("plugin.serialization")
    `maven-publish`
}

kotlin {
    androidTarget {
//        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
//    jvm()

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.http)
            implementation(libs.kamel.fetcher.resources.android)
            implementation(libs.kamel.decoder.image.bitmap.resizing) // android only right now
//            implementation(libs.kamel.http.client)

        }
        commonMain.dependencies {
            implementation(libs.runtime)
            implementation(libs.foundation)
            implementation(libs.material3)
            implementation(libs.ui)
            implementation(libs.components.resources)
            implementation(libs.ui.tooling.preview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.material.icons.extended)
            implementation(libs.ktor.core)
            implementation(libs.ktor.negotiation)
            implementation(libs.ktor.serialization)

            implementation(libs.kamel.image)
            implementation(libs.kamel.image.default)
            // optional modules (choose what you need and add them to your kamel config)
            implementation(libs.kamel.decoder.image.bitmap)
            implementation(libs.kamel.decoder.image.vector)
            implementation(libs.kamel.decoder.svg.std)
            implementation(libs.kamel.decoder.animated.image) // .gif support
        }

//        jvmMain {
//            dependencies {
//                // optional modules (choose what you need and add them to your kamel config)
//                implementation(libs.kamel.fetcher.resources.jvm)
//                // ...
//            }
//        }

        iosMain.dependencies {
            implementation(libs.ktor.darwin)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.fiuuxdklibrary"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.fiuuxdklibrary"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

publishing {
    publications {
        withType<MavenPublication> {
            groupId = "com.github.nikfaris88"
            version = "1.0.1"
        }
    }
}

