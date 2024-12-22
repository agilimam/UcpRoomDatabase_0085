package com.example.ucp2pam.ui.view.Barang

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.data.entity.Barang
import com.example.ucp2pam.ui.costumwidget.TopAppBar
import com.example.ucp2pam.ui.viewmodel.Barang.DetailBrgViewModel
import com.example.ucp2pam.ui.viewmodel.Barang.DetailUiStateBrg
import com.example.ucp2pam.ui.viewmodel.Barang.toBarangEntity
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel

@Composable
fun DetailBrgView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onDeleteClick: () -> Unit = {},
    viewModel: DetailBrgViewModel = viewModel(factory = PenyediaViewModel.Factory)

){
    Scaffold (
        topBar = {
            TopAppBar(
                judul = "Detail Mahasiswa",
                showBackButton = true,

                )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(viewModel.detailUiStateBrg.value.detailUiEventBrg.Nama) },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Barang",
                )
            }
        }

    ){ innerPadding ->
        val  detailUiStateBrg by viewModel.detailUiStateBrg.collectAsState()

        BodyDetailBrg(
            modifier = Modifier.padding(innerPadding),
            detailUiStateBrg = detailUiStateBrg,
            onDeleteClick = {
                viewModel.deleteBrg()
                onDeleteClick()
            }
        )
    }
}
@Composable
fun BodyDetailBrg(
    modifier: Modifier = Modifier,
    detailUiStateBrg: DetailUiStateBrg =DetailUiStateBrg(),
    onDeleteClick: () -> Unit = {}
){
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    when{
        detailUiStateBrg.isLoadingBrg -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator() //Tampilkan loading
            }
        }
        detailUiStateBrg.isUiEvenNotEmptyBrg -> {
            Column (
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                itemDetailBrg(
                    barang = detailUiStateBrg.detailUiEventBrg.toBarangEntity(),
                    modifier = modifier
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    onClick = {
                        deleteConfirmationRequired = true
                    } ,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Delete")
                }

                if (deleteConfirmationRequired){
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = {deleteConfirmationRequired = false},
                        modifier =  Modifier.padding(8.dp)
                    )
                }
            }
        }


        detailUiStateBrg.isUiEventEmptyBrg -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Data tidak ditemukan",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

}
@Composable
fun itemDetailBrg(
    modifier: Modifier = Modifier,
    barang: Barang
){
    Card (
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    ) {
        Column (
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailBrg(judul = "id", isinya = barang.id)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailBrg(judul = "Nama", isinya = barang.Nama)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailBrg(judul = "Deskripsi", isinya = barang.Deskripsi)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailBrg(judul = "Harga", isinya = barang.Harga)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailBrg(judul = "Stok", isinya = barang.Stok)
            Spacer(modifier = Modifier.padding(4.dp))
            ComponentDetailBrg(judul = "Namasuplier", isinya = barang.NamaSuplier)
            Spacer(modifier = Modifier.padding(4.dp))
        }

    }

}
@Composable
fun ComponentDetailBrg(
    modifier: Modifier = Modifier,
    judul : String,
    isinya: String,
){
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ){
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray

        )
        Text(
            text = isinya, fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )

    }

}
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit,modifier: Modifier = Modifier

) {
    AlertDialog(onDismissRequest = {/*  Do nothing */ },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
        modifier = Modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        })
}