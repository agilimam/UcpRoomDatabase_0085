package com.example.ucp2pam.ui.viewmodel.Barang

import com.example.ucp2pam.data.entity.Barang

data class DetailUiStateBrg(
    val detailUiEventBrg:BarangEvent =BarangEvent(),
    val isLoadingBrg: Boolean = false,
    val isErrorBrg: Boolean = false,
    val errorMessageBrg: String = ""

) {
    val isUiEventEmptyBrg: Boolean
        get() = detailUiEventBrg == BarangEvent()
    val isUiEvenNotEmptyBrg: Boolean
        get() = detailUiEventBrg != BarangEvent()
}

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