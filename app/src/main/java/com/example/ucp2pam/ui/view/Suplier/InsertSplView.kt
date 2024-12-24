package com.example.ucp2pam.ui.view.Suplier

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.ui.costumwidget.TopAppBar
import com.example.ucp2pam.ui.navigation.AlamatNavigasi
import com.example.ucp2pam.ui.viewmodel.Barang.FormErrorState
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import com.example.ucp2pam.ui.viewmodel.Suplier.FormErrorStateSpl
import com.example.ucp2pam.ui.viewmodel.Suplier.InsertSplViewModel
import com.example.ucp2pam.ui.viewmodel.Suplier.SplUIState
import com.example.ucp2pam.ui.viewmodel.Suplier.SuplierEvent
import kotlinx.coroutines.launch

object DestinasiInsertSpl: AlamatNavigasi {
    override val route = "Insert-Spl"
}

@Composable
fun InsertSplView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: InsertSplViewModel = viewModel(factory = PenyediaViewModel.Factory) //Inisialisasi ViewModel
) {
    val uiState = viewModel.uiState // Ambil UI State dari viewmodel
    val snackbarHostState =  remember { SnackbarHostState() } // Snack
    val coroutineScope = rememberCoroutineScope()

    // Observasi perubahan snackBarMessage
    LaunchedEffect(uiState.snackBarMessage)  {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Suplier",
            )
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            //Isi Body
            InsertBodySpl(
                uiState = uiState,
                onvalueChange = { updateEvent ->
                    //Update state di viewmodel
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    viewModel.saveData()
                    if(uiState.snackBarMessage == "data berhasil disimpan"){
                        onNavigate()
                    }
                    onNavigate()
                }
            )

        }

    }
}
@Composable
fun InsertBodySpl(
    modifier: Modifier = Modifier,
    onvalueChange: (SuplierEvent) -> Unit,
    uiState: SplUIState,
    onClick: () -> Unit
){
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormSuplier(
            suplierEvent = uiState.suplierEvent,
            onvalueChange = onvalueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            colors = buttonColors(
                containerColor = Color(0XFF09f9d2)
            )
        ) {
            Text(text = "Simpan")
        }

    }
}
@Preview(showBackground = true)
@Composable
fun  FormSuplier(
    suplierEvent: SuplierEvent = SuplierEvent(),
    onvalueChange: (SuplierEvent)-> Unit = {},
    errorState: FormErrorStateSpl = FormErrorStateSpl(),
    modifier: Modifier = Modifier
){
    Column (
        modifier = Modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.Nama,
            onValueChange = {
                onvalueChange(suplierEvent.copy(Nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.Nama != null,
            placeholder = { Text("Masukkan Nama") }
        )
        Text(
            text = errorState.Nama?: "",
            color = Color.Red
        )
        Spacer(modifier =Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.Kontak,
            onValueChange = {
                onvalueChange(suplierEvent.copy(Kontak = it))
            },
            label = { Text("Kontak") },
            isError = errorState.Kontak != null,
            placeholder = { Text("Masukkan Kontak") }
        )
        Text(
            text = errorState.Kontak?: "",
            color = Color.Red
        )
        Spacer(modifier =Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = suplierEvent.Alamat,
            onValueChange = {
                onvalueChange(suplierEvent.copy(Alamat = it))
            },
            label = { Text("Alamat") },
            isError = errorState.Alamat != null,
            placeholder = { Text("Masukkan Alamat") }
        )
        Text(
            text = errorState.Alamat?: "",
            color = Color.Red
        )
        Spacer(modifier =Modifier.height(10.dp))

    }
}
