package com.fiuu.xdklibrary.c2p

import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun C2PWebView(
    configJson: String,
    detailsJson: String,
    onResult: (Result<String>) -> Unit
) {
    val context = LocalContext.current
    var hasLoaded by remember { mutableStateOf(false) }

    val sdkDataJson: String = """
        {
            mp_amount: '1.10',
            mp_company: 'Test Company',
            mp_dpa_id: '82d6652a-60c6-4f2d-9c8a-47b7ff592321',
            mp_bill_email:"atikahamin91@gmail.com",
            mp_bill_mobile_country: 'MY',
            mp_bill_mobile:'0173705863'
         }
    """.trimIndent()

    AndroidView(factory = { ctx ->
        WebView(ctx).apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.allowFileAccess = false
            settings.allowContentAccess = false
            webChromeClient = WebChromeClient()

            // JS -> Kotlin bridge
            addJavascriptInterface(
                AndroidBridge(
                    onSuccess = { json -> onResult(Result.success(json)) },
                    onError = { json -> onResult(Result.failure(Exception(json))) },
                    onShidi = { payload -> println("C2P BLABLABLA: $payload")}
                ),
                "AndroidBridge"
            )

            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    println("SDKData: $sdkDataJson")
                    val js = """
            window.startC2Pay($sdkDataJson);
        """.trimIndent()

                    evaluateJavascript(js, null)
                }
            }
            loadUrl("https://test.shidistudio.com/checkout")
//            webViewClient = object : WebViewClient() {
//                override fun onPageFinished(view: WebView?, url: String?) {
//                    super.onPageFinished(view, url)
//
//                    // Initialize and call Mastercard SRC immediately
//                    val jsInit = """
//                        (function () {
//
//                            const c2p = new window.Click2Pay();
//                            console.log("sdkDataJson", $sdkDataJson, c2p);
//
//                            const sdkData = $sdkDataJson;
//
//                            const initParams = {
//                                srcDpaId: sdkData.mp_dpa_id,
//                                cardBrands: sdkData.mp_bill_description?.includes("ZMC2P")
//                                    ? ["mastercard"]
//                                    : ["mastercard", "visa"],
//                                dpaTransactionOptions: {
//                                    dpaLocale: "en_MY",
//                                    confirmPayment: false,
//                                    transactionAmount: {
//                                        transactionAmount: parseFloat(sdkData.mp_amount),
//                                        transactionCurrencyCode: sdkData.mp_currency || "MYR"
//                                    }
//                                },
//                                dpaData: {
//                                    dpaName: sdkData.mp_company || ""
//                                }
//                            };
//
//                            console.log("initMCARD", initParams);
//
//                            // Init SRC
//                            const result = c2p.init(initParams)
//                                .then(async () => {
//                                    // Get saved cards
//                                    // const cards = c2p.getCards();
//                                    // AndroidBridge.onSuccess(JSON.stringify({
//                                    //     type: "INIT_SUCCESS",
//                                    //     cards
//                                    // }));
//
//                                    // // Optional: auto-start checkout with first card
//                                    // if (cards.length > 0) {
//                                    //     await c2p.checkoutWithNewCard({
//                                    //         transactionAmount: parseFloat(sdkData.mp_amount),
//                                    //         transactionCurrencyCode: sdkData.mp_currency || "MYR"
//                                    //     }).then(response => {
//                                    //         AndroidBridge.onSuccess(JSON.stringify({
//                                    //             type: "CHECKOUT_SUCCESS",
//                                    //             response
//                                    //         }));
//                                    //     });
//                                    // }
//
//                                    console.info('result', result);
//                                    const availableCardBrands = result.availableCardBrands || [];
//                                    console.info('availableCardBrands', availableCardBrands);
//                                    const cards = await c2p.getCards();
//                                    console.info('cards', cards);
//
//                                    if (cards.length == 0) {
//                                        console.log('No cards available');
//
//                                        // const updatedData = {
//                                        //   mp_credit_card_bank: sdkData?.mp_credit_card_bank || '',
//                                        //   mp_credit_card_no: (sdkData?.mp_credit_card_no_display ?? '').replace(/\s/g, ''),
//                                        //   mp_credit_card_expiry: sdkData?.month+sdkData?.year,
//                                        //   month: sdkData?.month,
//                                        //   year: sdkData?.year,
//                                        //   mp_credit_card_cvv: sdkData?.mp_credit_card_cvv,
//                                        //   card_type: cardService.cardTypeCheck(
//                                        //     (sdkData?.mp_credit_card_no_display ?? '').replace(/\s/g, ''),
//                                        //   ),
//                                        // }
//
//
//                                        // sdkData = {...sdkData,...updatedData};
//
//
//
//                                        const phoneNumberParams = {
//                                            countryCode: sdkData.mp_bill_mobile_country?.trim() || '',
//                                            phoneNumber: sdkData.mp_bill_mobile_number?.trim() || '',
//                                        };
//
//                                        const email = sdkData.mp_bill_email?.trim();
//
//                                        if (phoneNumberParams.countryCode && phoneNumberParams.phoneNumber) {
//                                            console.info('idLookup mobileNumber', { mobileNumber: phoneNumberParams });
//                                            try {
//                                                const lookupResultmobileNumber = await c2p.idLookup({ mobileNumber: phoneNumberParams });
//                                                console.info('lookupResult mobileNumber', lookupResultmobileNumber);
//                                            } catch (error) {
//                                                console.error('Error in idLookup mobileNumber:', error);
//                                            }
//                                        }
//
//                                        if (email) {
//                                            console.info('idLookup email', { email: email });
//                                            try {
//                                                const lookupResultemail = await c2p.idLookup({ email: email });
//                                                console.info('lookupResult email', lookupResultemail);
//                                            } catch (error) {
//                                                console.error('Error in idLookup email:', error);
//                                            }
//                                        }
//
//                                        const result2 = await c2p.initiateValidation()
//                                        console.info('result2', result2);
//
//
//                                    }
//                                })
//                        })();
//                    """.trimIndent()
//
//                    evaluateJavascript(jsInit, null)
//                }
//            }

//            if (!hasLoaded) {
//                val htmlContent = """
//                    <!DOCTYPE html>
//                    <html>
//                    <head>
//                    <script type="module" src="https://src.mastercard.com/srci/integration/components/src-ui-kit/src-ui-kit.esm.js"></script>
//
//                        <script src="https://src.mastercard.com/srci/integration/2/lib.js?dpaId=82d6652a-60c6-4f2d-9c8a-47b7ff592321&locale=en_MY"></script>
//                    </head>
//                    <body>
//                        <div id="c2p-container">
//
//                                                <src-otp-input></src-otp-input>
//
//                        </div>
//
//                    </body>
//                    </html>
//                """.trimIndent()
//
//                loadDataWithBaseURL("https://app.local/", htmlContent, "text/html", "utf-8", null)
//                hasLoaded = true
//            }
        }
    })
}