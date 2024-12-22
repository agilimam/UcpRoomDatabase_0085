package com.example.ucp2pam.ui.viewmodel.Barang

import com.example.ucp2pam.data.entity.Barang

// Memindahkan data dari entity ke ui

fun Barang.toDetailUiEventBrg(): BarangEvent{
    return BarangEvent(
        id = id,
        Nama = Nama,
        Deskripsi = Deskripsi,
        Harga = Harga,
        Stok = Stok,
        NamaSuplier = NamaSuplier,
    )
}