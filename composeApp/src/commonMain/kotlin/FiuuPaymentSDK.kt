import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.fiuu.xdklibrary.data.remote.HttpClientFactory
import com.fiuu.xdklibrary.data.remote.KtorApiService
import com.fiuu.xdklibrary.data.remote.dto.PaymentResponseDto
import com.fiuu.xdklibrary.data.repository.FiuuPaymentRepository
import com.fiuu.xdklibrary.domain.entity.toPaymentRequest
import com.fiuu.xdklibrary.platform.WebViewBridge
import com.fiuu.xdklibrary.ui.PaymentMultiChannel
import com.fiuu.xdklibrary.ui.model.PaymentScreen
import com.fiuu.xdklibrary.viewmodel.PaymentViewModel



val localWebViewBridge = staticCompositionLocalOf<WebViewBridge> {
    error("WebViewBridge not provided")
}

@Composable
fun FiuuPaymentSDK(
    params: Map<String, Any?>,
    onPaymentResult: (PaymentResponseDto) -> Unit,
    onRequestClose: () -> Unit
) {
//    CheckoutScreen()
    val webViewBridge = localWebViewBridge.current

    val vm = remember { PaymentViewModel(FiuuPaymentRepository(
        KtorApiService(
            HttpClientFactory()
        )
    )) }
    var screen by remember { mutableStateOf<PaymentScreen>(PaymentScreen.Methods) }

    LaunchedEffect(params) {
        vm.attachWebViewBridge(webViewBridge)
    }

    LaunchedEffect(params) {
        vm.initialize(params)
    }

    //check params if express right away startPayment else open paymentflow
    val paymentRequest = remember(params){
        params.toPaymentRequest()
    }
    LaunchedEffect(paymentRequest.mpExpressMode) {
        if (paymentRequest.mpExpressMode) {
            vm.startPayment()
        }
    }

    if (!paymentRequest.mpExpressMode) {
        PaymentMultiChannel(
            vm = vm,
            paymentRequest = paymentRequest,
            screen = screen,
            showAppBar = true,
            onScreenChange = { screen = it },
            onRequestClose = onRequestClose

        )
    }
}
//
//@Composable
//fun StartPaymentWebView(
//    params: Map<String, Any?>,
//    webViewBridge: WebViewBridge,
//    onPaymentResult: (PaymentResponseDto) -> Unit,
//    onRequestClose: () -> Unit
//) {
//    val vm = remember { PaymentViewModel(FiuuPaymentRepository(
//        KtorApiService(HttpClientFactory())
//    )) }
//    val lifecycleOwner = LocalLifecycleOwner.current
//
//    LaunchedEffect(params) {
//        vm.startPaymentWebView(webViewBridge)
//    }
//
//    DisposableEffect(lifecycleOwner) {
//        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_RESUME) {
//                // Called when app comes back to foreground (after TNG app)
//                println("FIUU ONRESUME")
//            }
//
//            if (event == Lifecycle.Event.ON_DESTROY) {
//                println("FIUU ONDESTROY")
//            }
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    }
//
//    // Optional: UI for payment flow if needed
//    params.toPaymentRequest()
//}