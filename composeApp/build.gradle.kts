import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization")
    `maven-publish`
}

compose.resources {
    // IMPORTANT for publishing: make generated `Res` stable across environments (incl. JitPack)
    packageOfResClass = "com.fiuu.xdklibrary.generated.resources"
    publicResClass = true
}

kotlin {
    val xcframework = XCFramework()

    androidTarget {
        publishLibraryVariants("release")
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
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

            binaryOption(
                "bundleId",
                "com.fiuu.payment.compose"
            )

            xcframework.add(this)
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
            implementation(libs.androidx.webkit.v1120)
            implementation(libs.androidx.browser.v150)

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
    namespace = "com.fiuu.xdklibrary"
    compileSdk = 36

    defaultConfig {
        minSdk = 33
        lint.targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    debugImplementation(libs.ui.tooling)
}

publishing {
    publications {
        withType<MavenPublication> {
            groupId = "com.github.nikfaris88"
            artifactId = "FiuuXDKLibrary"
            version = "1.0.4"
        }
    }
}

