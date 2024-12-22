package com.example.ucp2pam.ui.viewmodel.Suplier

import com.example.ucp2pam.data.entity.Barang

data class HomeUiState(
    val listBrg: List<Barang> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)