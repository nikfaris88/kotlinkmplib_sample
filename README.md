FiuuXDKLibrary is a **Kotlin Multiplatform (KMP)** SDK targeting **Android + iOS**.

## Project layout

- **`composeApp/`**: the publishable KMP library (Android AAR + iOS framework).
- **`sampleAndroidApp/`**: Android sample app that uses the library.
- **`iosApp/`**: iOS sample app (SwiftUI) that uses the generated `ComposeApp` framework.

## Android usage (via JitPack)

### 1) Add JitPack repository

In your app's `settings.gradle(.kts)`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
```

### 2) Add the dependency

In your Android app module `build.gradle(.kts)`:

```kotlin
dependencies {
    implementation("com.github.nikfaris88:FiuuXDKLibrary:<TAG_OR_VERSION>")
}
```

> Replace `<TAG_OR_VERSION>` with your Git tag, e.g. `1.0.4` or `v1.0.4` depending on how you tagged in GitHub/JitPack.

### 3) Use the SDK from Compose

From your Android UI:

```kotlin
FiuuPaymentSDK(
    params = paymentParams,
    onPaymentResult = { /* handle result */ },
    onRequestClose = { /* close flow */ }
)
```

## Native Android (Kotlin) integration example

### Jetpack Compose

```kotlin
val paymentParams = mapOf<String, Any?>(
    // TODO: fill in required fields
)

FiuuPaymentSDK(
    params = paymentParams,
    onPaymentResult = { result ->
        // TODO handle PaymentResponseDto
    },
    onRequestClose = { /* finish() / popBackStack() */ }
)
```

## iOS usage (via the generated KMP framework)

iOS does **not** consume this SDK via JitPack. For iOS, you integrate the KMP framework (`ComposeApp`) built from the project.

### Option A (recommended for this repo): run the sample iOS app

1) Open `iosApp/iosApp.xcodeproj` in Xcode  
2) Select the `iosApp` scheme  
3) Build/Run

The project includes a build phase that runs:

- `./gradlew :composeApp:embedAndSignAppleFrameworkForXcode`

This generates and links the `ComposeApp.framework` automatically.

### Calling Kotlin from Swift

In Swift, import the framework and call the exported function from `startFiuuPaymentIos.kt`:

```swift
import SwiftUI
import UIKit
import ComposeApp

let params: [AnyHashable: Any] = [
    "payment_url": "https://your-payment-url.com/..."
]

let vc = StartFiuuPaymentIosKt.startFiuuPaymentIos(paymentParams: params)
rootVC.present(vc, animated: true)
```

## Native iOS (Swift) integration example

### SwiftUI button → present the payment UIViewController

```swift
import SwiftUI
import UIKit
import ComposeApp

struct ContentView: View {
    var body: some View {
        Button("Start Fiuu Payment") {
            guard let rootVC = UIApplication.shared.connectedScenes
                .compactMap({ $0 as? UIWindowScene })
                .first?.windows.first?.rootViewController
            else { return }

            let params: [AnyHashable: Any] = [
                "payment_url": "https://your-payment-url.com/..."
            ]

            let vc = StartFiuuPaymentIosKt.startFiuuPaymentIos(paymentParams: params)
            rootVC.present(vc, animated: true)
        }
    }
}
```

## Hybrid apps

Hybrid frameworks (Flutter / React Native / Capacitor) typically call into native code.
The recommended approach is:

- **Android**: add the SDK in the Android host app (Gradle), then expose a small native bridge (MethodChannel / Native Module / Capacitor Plugin) that triggers `FiuuPaymentSDK`.
- **iOS**: integrate the `ComposeApp` framework (Xcode), then expose a small native bridge that presents `StartFiuuPaymentIosKt.startFiuuPaymentIos(...)`.

### Flutter (MethodChannel)

- **Android**: add the JitPack dependency in `android/app/build.gradle(.kts)` and implement a `MethodChannel` handler that launches your payment UI.
- **iOS**: integrate the `ComposeApp` framework into `Runner.xcodeproj`, then in `AppDelegate.swift` handle the channel method and present the `UIViewController` returned by `StartFiuuPaymentIosKt.startFiuuPaymentIos(...)`.

### React Native (CLI) (Native Module)

- **Android**: add the dependency in `android/app/build.gradle`, create a `NativeModule` that shows the payment UI.
- **iOS**: add `ComposeApp` framework to the Xcode project, create an Objective‑C/Swift native module that presents the payment `UIViewController`.

### Expo (managed) / Expo Dev Client

Expo managed workflow cannot use custom native code unless you:
- use **Expo Dev Client** (`expo prebuild`) or
- build via **EAS Build** with native modules.

Once you’re in a prebuild/dev-client workflow, follow the same approach as React Native CLI above.

### Ionic Capacitor

- **Android**: add the dependency in the native Android project under `android/` and write a Capacitor plugin that triggers `FiuuPaymentSDK`.
- **iOS**: add `ComposeApp` framework under `ios/` and write a Capacitor plugin that presents `StartFiuuPaymentIosKt.startFiuuPaymentIos(...)`.

### Preflight build (useful before tagging / JitPack)

Run these locally to catch iOS compilation issues early:

```bash
./gradlew :composeApp:assembleRelease \
         :composeApp:compileKotlinIosArm64 \
         :composeApp:compileKotlinIosSimulatorArm64
```

---

Learn more about Kotlin Multiplatform: `https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html`