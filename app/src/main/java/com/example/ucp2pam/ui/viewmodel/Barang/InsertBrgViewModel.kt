package com.example.ucp2pam.ui.viewmodel.Barang

import com.example.ucp2pam.data.entity.Barang

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
