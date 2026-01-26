import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.fiuuxdklibrary.data.remote.HttpClientFactory
import com.example.fiuuxdklibrary.data.remote.KtorApiService
import com.example.fiuuxdklibrary.data.remote.dto.PaymentResponseDto
import com.example.fiuuxdklibrary.data.repository.FiuuPaymentRepository
import com.example.fiuuxdklibrary.domain.entity.toPaymentRequest
import com.example.fiuuxdklibrary.platform.WebViewBridge
import com.example.fiuuxdklibrary.ui.PaymentMultiChannel
import com.example.fiuuxdklibrary.ui.model.PaymentScreen
import com.example.fiuuxdklibrary.viewmodel.PaymentViewModel

@Composable
fun FiuuPaymentSDK(
    params: Map<String, Any?>,
    onPaymentResult: (PaymentResponseDto) -> Unit,
    onRequestClose: () -> Unit
) {

    val localWebViewBridge = staticCompositionLocalOf <WebViewBridge> {
        error("Webviewbridge not provided")
    }

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