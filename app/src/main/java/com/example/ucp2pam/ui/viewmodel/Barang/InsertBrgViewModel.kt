package com.example.ucp2pam.ui.viewmodel.Barang

import com.example.ucp2pam.data.entity.Barang

data class FormErrorStateBrg(
    val id: String? = null,
    val Nama: String? =null,
    val Deskripsi: String? =null,
    val Harga: String? =null,
    val Stok: String? =null,
    val NamaSuplier: String? =null,
)
fun BarangEvent.toBarangEntity():Barang = Barang(
    id = id,
    Nama = Nama,
    Deskripsi = Deskripsi,
    Harga = Harga,
    Stok = Stok,
    NamaSuplier = NamaSuplier
)
data class BarangEvent(
    val id: String = "",
    val Nama: String = "",
    val Deskripsi: String = "",
    val Harga: String = "",
    val Stok: String = "",
    val NamaSuplier: String = "",
)
