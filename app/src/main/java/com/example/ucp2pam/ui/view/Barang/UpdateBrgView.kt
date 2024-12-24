package com.example.ucp2pam.ui.view.Barang

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.ui.costumwidget.TopAppBar
import com.example.ucp2pam.ui.viewmodel.Barang.UpdateBrgViewModel
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun UpdateBrgView(
    onBack : () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateBrgViewModel = viewModel(factory = PenyediaViewModel.Factory)// Inisialisai Viewmodel
){
    val uiState = viewModel.updateUiState// ambil UI state dari viewModel
    val snackbarHostState = remember { SnackbarHostState() }//Snaclbar State
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackbarMassage

    LaunchedEffect(uiState) {
        println("LaunchedEffect triggered")
        uiState.snackBarMessage?.let { message ->
            println("Snackbar message received: $message")
            coroutineScope.launch {
                println("Launching coroutine for snackbar")
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold (
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, //tempatkan snackbar di scaffold
        topBar = {
            TopAppBar(
                judul = "Edit Barang",
                showBackButton = true,
                onBack = onBack,

                )
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){

            // isi Body
            InsertBodyBrg(
                uiState = uiState,
                onvalueChange = {updatedEvent ->
                    viewModel.updateState(updatedEvent)//update state di viewmodel
                },
                onClick = {
                    coroutineScope.launch {
                        if (viewModel.validateFields()){
                            viewModel.updateData()
                            delay(600)
                            withContext(Dispatchers.Main){
                                onNavigate()//Navigasi di main thread
                            }
                        }
                    }
                }
            )
        }

    }
}