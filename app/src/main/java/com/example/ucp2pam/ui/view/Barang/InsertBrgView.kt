package com.example.ucp2pam.ui.view.Barang

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.ui.costumwidget.TopAppBar
import com.example.ucp2pam.ui.navigation.AlamatNavigasi
import com.example.ucp2pam.ui.viewmodel.Barang.BarangEvent
import com.example.ucp2pam.ui.viewmodel.Barang.BrgUiState
import com.example.ucp2pam.ui.viewmodel.Barang.FormErrorStateBrg
import com.example.ucp2pam.ui.viewmodel.Barang.InsertBrgViewModel
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch

object DestinasiInsertBrg: AlamatNavigasi {
    override val route = "Insert-Brg"
}
@Composable
fun FormBrg(
    barangEvent: BarangEvent = BarangEvent(),
    onvalueChange: (BarangEvent) -> Unit = { },
    errorStateBrg: FormErrorStateBrg = FormErrorStateBrg(),
    modifier: Modifier = Modifier
) {
    Column (
        modifier = Modifier.fillMaxWidth()
    ){
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.id,
            onValueChange = {
                onvalueChange(barangEvent.copy(id = it))
            },
            label = { Text("ID") },
            isError = errorStateBrg.id != null,
            placeholder = { Text("Masukkan id") }
        )
        Text(
            text = errorStateBrg.id ?: "",
            color = Color.Red
        )
        Spacer(modifier =Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.Nama,
            onValueChange = {
                onvalueChange(barangEvent.copy(Nama = it))
            },
            label = { Text("Nama") },
            isError = errorStateBrg.Nama != null,
            placeholder = { Text("Masukkan Nama Barang") }
        )
        Text(
            text = errorStateBrg.Nama?: "",
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
            isError = errorStateBrg.Deskripsi != null,
            placeholder = { Text("Masukkan Deskripsi") }
        )
        Text(
            text = errorStateBrg.Deskripsi?: "",
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
            isError = errorStateBrg.Harga != null,
            placeholder = { Text("Masukkan Harga") }
        )
        Text(
            text = errorStateBrg.Harga?: "",
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
            isError = errorStateBrg.Stok != null,
            placeholder = { Text("Masukkan Stok") }
        )
        Text(
            text = errorStateBrg.Stok?: "",
            color = Color.Red
        )
        Spacer(modifier =Modifier.height(10.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.NamaSuplier,
            onValueChange = {
                onvalueChange(barangEvent.copy(NamaSuplier = it))
            },
            label = { Text("NamaSuplier") },
            isError = errorStateBrg.Stok != null,
            placeholder = { Text("Masukkan NamaSuplier") }
        )
        Text(
            text = errorStateBrg.NamaSuplier?: "",
            color = Color.Red
        )


    }

}
@Composable
fun InsertBrgView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: InsertBrgViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiStateBrg = viewModel.uiStateBrg
    val snackbarHostStateBrg = remember { SnackbarHostState() }
    val corutineScope = rememberCoroutineScope()

    LaunchedEffect(uiStateBrg.snackBarMessageBrg) {
        uiStateBrg.snackBarMessageBrg?.let { message ->
            corutineScope.launch {
                try {
                    snackbarHostStateBrg.showSnackbar(message)
                } catch (e: Exception) {
                    Log.e("SnackbarError", "Error showing snackbar: ${e.message}")
                } finally {
                    viewModel.resetSnackBarMessageBrg()
                    Log.d("SnackbarState", "Snackbar message reset")
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostStateBrg) }
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ){
            TopAppBar(
                showBackButton = true,
                judul = "Tambah Barang",
                onBack = onBack,

            )

            //Isi Body
            InsertBodyBrg(
                uiState = uiStateBrg,
                onValueChange = { updateEvent ->
                    //Update state di viewmodel
                    viewModel.updateState(updateEvent)
                },
                onClick = {
                    viewModel.saveDataBrg()
                    onNavigate()
                }
            )

        }

    }

}
@Composable
fun InsertBodyBrg(
    modifier: Modifier = Modifier,
    onValueChange: (BarangEvent) -> Unit,
    uiState: BrgUiState,
    onClick: () -> Unit
){
    Column (
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormBrg(
            barangEvent = uiState.barangEvent,
            onvalueChange = onValueChange,
            errorStateBrg = uiState.isEntryValidBrg,
            modifier = Modifier.fillMaxWidth()

        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Simpan")
        }

    }
}