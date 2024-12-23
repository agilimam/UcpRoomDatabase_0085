package com.example.ucp2pam.ui.view.Suplier

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.data.entity.Suplier
import com.example.ucp2pam.ui.costumwidget.TopAppBar
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import com.example.ucp2pam.ui.viewmodel.Suplier.HomeSplViewModel
import com.example.ucp2pam.ui.viewmodel.Suplier.HomeUIState
import kotlinx.coroutines.launch


@Composable
fun HomeSPlView(
    viewModel: HomeSplViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBack: ()->Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                judul = "Daftar Suplier",
                showBackButton = true,
                onBack = onBack,

                )
        },
    ) { innerPadding ->
        val homeUIState by viewModel.homeUIState.collectAsState()

        BodyHomeSplView(
            homeUIState = homeUIState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
@Composable
fun BodyHomeSplView(
    homeUIState: HomeUIState,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() } // Snackbar state
    when {
        homeUIState.isLoading -> {
            // Menampilkan indikator loading
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        homeUIState.isError -> {
            //Menampilkan pesarn error
            LaunchedEffect(homeUIState.errorMessage) {
                homeUIState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message) //Tampilkan Snackbar
                    }
                }
            }
        }

        homeUIState.listSpl.isEmpty() -> {
            // Menampilkan pesan jika data kosong
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data Suplier.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        else -> {
            //Menampilkan daftar Suplier
            ListSuplier(
                listSpl = homeUIState.listSpl,
                modifier = modifier
            )
        }
    }
}

@Composable
fun ListSuplier(
    listSpl: List<Suplier>,
    modifier: Modifier = Modifier,
) {
    LazyColumn (
        modifier = modifier
    ) {
        items(
            items = listSpl,
            itemContent = { spl ->
                CardSpl(
                    spl = spl,
                )
            }
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardSpl(
    spl: Suplier,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    Card (
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = spl.Nama,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.Phone, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = spl.Kontak,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "")
                Spacer(modifier = Modifier.padding(4.dp))
                Text(
                    text = spl.Alamat,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}