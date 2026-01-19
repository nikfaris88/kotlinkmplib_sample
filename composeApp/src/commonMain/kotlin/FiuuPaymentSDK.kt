import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.fiuuxdklibrary.data.remote.dto.PaymentResponseDto
import com.example.fiuuxdklibrary.data.repository.FiuuPaymentRepository
import com.example.fiuuxdklibrary.ui.PaymentFlow
import com.example.fiuuxdklibrary.ui.model.PaymentScreen
import com.example.fiuuxdklibrary.viewmodel.PaymentViewModel

@Composable
fun FiuuPaymentSDK(
    params: Map<String, Any>,
    onPaymentResult: (PaymentResponseDto) -> Unit,
    onRequestClose: () -> Unit
) {
    val vm = remember { PaymentViewModel(FiuuPaymentRepository()) }
    var screen by remember { mutableStateOf<PaymentScreen>(PaymentScreen.Methods) }

    LaunchedEffect(params) {
        vm.initialize(params)
    }

    PaymentFlow(
        vm = vm,
        screen = screen,
        showAppBar = true,
        onScreenChange = { screen = it },
        onRequestClose = { onRequestClose }

    )
}