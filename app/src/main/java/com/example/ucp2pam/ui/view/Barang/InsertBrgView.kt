package com.example.ucp2pam.ui.view.Barang

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.data.SuplierList
import com.example.ucp2pam.ui.costumwidget.DynamicSelectTextField
import com.example.ucp2pam.ui.costumwidget.TopAppBar
import com.example.ucp2pam.ui.navigation.AlamatNavigasi
import com.example.ucp2pam.ui.viewmodel.Barang.BarangEvent
import com.example.ucp2pam.ui.viewmodel.Barang.BrgUIState
import com.example.ucp2pam.ui.viewmodel.Barang.FormErrorState
import com.example.ucp2pam.ui.viewmodel.Barang.InsertBrgViewModel
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertBrg: AlamatNavigasi {
    override val route = "Insert-Brg"
}

@Composable
fun InsertBrgView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: InsertBrgViewModel = viewModel(factory = PenyediaViewModel.Factory) //Inisialisasi ViewModel
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)},
        topBar = {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Barang",
                modifier = modifier
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
            InsertBodyBrg(
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
                }
            )

        }

    }
}
@Composable
fun InsertBodyBrg(
    modifier: Modifier = Modifier,
    onvalueChange: (BarangEvent) -> Unit,
    uiState: BrgUIState,
    onClick: () -> Unit
){
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormBarang(
            barangEvent = uiState.barangEvent,
            onvalueChange = onvalueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            colors = buttonColors(
                containerColor = Color(0XFF09f9d2)
            ),
        ) {
            Text(text = "Simpan")
        }

    }
}
@Preview(showBackground = true)
@Composable
fun  FormBarang(
    barangEvent: BarangEvent = BarangEvent(),
    onvalueChange: (BarangEvent)-> Unit = {},
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier
){
    Column (
        modifier = Modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.Nama,
            onValueChange = {
                onvalueChange(barangEvent.copy(Nama = it))
            },
            label = { Text("Nama") },
            isError = errorState.Nama != null,
            placeholder = { Text("Masukkan Nama Barang") }
        )
        Text(
            text = errorState.Nama?: "",
            color = Color.Red
        )
        Spacer(modifier =Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.Deskripsi,
            onValueChange = {
                onvalueChange(barangEvent.copy(Deskripsi = it))
            },
            label = { Text("Deskripsi") },
            isError = errorState.Deskripsi  != null,
            placeholder = { Text("Masukkan Deskripsi") }
        )
        Text(
            text = errorState.Deskripsi?: "",
            color = Color.Red
        )
        Spacer(modifier =Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.Harga,
            onValueChange = {
                onvalueChange(barangEvent.copy(Harga = it))
            },
            label = { Text("Harga") },
            isError = errorState.Harga  != null,
            placeholder = { Text("Masukkan Harga") }
        )
        Text(
            text = errorState.Harga?: "",
            color = Color.Red
        )
        Spacer(modifier =Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.Stok,
            onValueChange = {
                onvalueChange(barangEvent.copy(Stok = it))
            },
            label = { Text("Stok") },
            isError = errorState.Stok  != null,
            placeholder = { Text("Masukkan Stok") }
        )
        Text(
            text = errorState.Stok?: "",
            color = Color.Red
        )
        Spacer(modifier =Modifier.height(10.dp))

        DynamicSelectTextField(
            modifier = Modifier,
            selectedValue = barangEvent.NamaSuplier,
            label = "Nama Suplier",
            onValueChangedEvent = { selectedSpl ->
                onvalueChange(barangEvent.copy(NamaSuplier = selectedSpl))
            },
            options = SuplierList.DataSpl()

        )
    }
}

