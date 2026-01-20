import UIKit
import SwiftUI
import ComposeApp
import Foundation

struct ContentView: View {
    var body: some View {
        Button("Start Fiuu Payment") {
            if let rootVC = UIApplication.shared.connectedScenes
                .compactMap({ $0 as? UIWindowScene })
                .first?.windows.first?.rootViewController {

                let params: [AnyHashable: Any] = [
                    "payment_url": "https://your-payment-url.com/..." // build this properly
                ]

                let vc = StartFiuuPaymentIosKt.startFiuuPaymentIos(paymentParams: params)
                rootVC.present(vc, animated: true)
            }
        }
    }
}



