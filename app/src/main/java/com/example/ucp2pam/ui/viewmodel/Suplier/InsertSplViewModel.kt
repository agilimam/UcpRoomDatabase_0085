package com.example.ucp2pam.ui.viewmodel.Suplier

import com.example.ucp2pam.ui.viewmodel.Barang.BarangEvent
import com.example.ucp2pam.ui.viewmodel.Barang.FormErrorState


data class SuplierEvent(
    val id: Int? = null,
    val Nama: String = "",
    val Kontak: String = "",
    val Alamat: String = "",
)