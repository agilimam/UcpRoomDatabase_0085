package com.example.ucp2pam.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2pam.ui.viewmodel.PenyediaViewModel
import com.example.ucp2pam.ui.viewmodel.Suplier.HomeSplViewModel

object SuplierList {

    @Composable
    fun DataSpl(
        splHome : HomeSplViewModel = viewModel(factory = PenyediaViewModel.Factory)

    ) : List<String>{
        val getDataNamaSpl by splHome.homeUIState.collectAsState()
        val namaSpl = getDataNamaSpl.listSpl.map {it.Nama}
        return namaSpl

    }
}