package com.example.ucp2pam.ui.viewmodel.Barang

import com.example.ucp2pam.data.entity.Barang

data class HomeBrgUiState(
    val listBrg: List<Barang> = listOf(),
    val isLoadingBrg: Boolean =false,
    val isErrorBrg : Boolean = false,
    val errorMessageBrg : String = ""
)