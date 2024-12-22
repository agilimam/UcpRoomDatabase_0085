package com.example.ucp2pam.ui.viewmodel.Barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Barang
import com.example.ucp2pam.data.repository.RepositoryBarang
import kotlinx.coroutines.launch

class InsertBrgViewModel (private val repositoryBarang: RepositoryBarang) : ViewModel(){
    var uiStateBrg by mutableStateOf(BrgUiState())

    fun updateState(barangEvent: BarangEvent){
        uiStateBrg = uiStateBrg.copy(
            barangEvent = barangEvent,
        )

    }private fun validataFields():Boolean{
        val event = uiStateBrg.barangEvent
        val errorStateBrg = FormErrorStateBrg(
            id = if (event.id.isNotEmpty())null else " Id tidak boleh kosong",
            Nama = if (event.Nama.isNotEmpty())null else " Nama tidak boleh kosong",
            Deskripsi = if (event.Deskripsi.isNotEmpty())null else " Deskripsi tidak boleh kosong",
            Harga = if (event.Harga.isNotEmpty())null else " harga tidak boleh kosong",
            Stok = if (event.Stok.isNotEmpty())null else " Stok tidak boleh kosong",
            NamaSuplier = if (event.NamaSuplier.isNotEmpty())null else " Namasuplier tidak boleh kosong",
        )
        uiStateBrg = uiStateBrg.copy(isEntryValidBrg = errorStateBrg)
        return errorStateBrg.isValidBrg()
    }
    fun saveDataBrg (){
        val currentEvent = uiStateBrg.barangEvent

        if (validataFields()){
            viewModelScope.launch {
                try {
                    repositoryBarang.insertBarang(currentEvent.toBarangEntity())
                    uiStateBrg = uiStateBrg.copy(
                        snackBarMessageBrg = "data berhasil disimpan",
                        barangEvent = BarangEvent(),
                        isEntryValidBrg = FormErrorStateBrg(),
                    )
                }catch (e: Exception){
                    uiStateBrg = uiStateBrg.copy(
                        snackBarMessageBrg = "data gagal disimpan"
                    )
                }
            }
        }else {
            uiStateBrg = uiStateBrg.copy(
                snackBarMessageBrg = "input tidak valid periksa data kembali"
            )
        }
    }
    fun resetSnackBarMessageBrg(){
        uiStateBrg = uiStateBrg.copy(snackBarMessageBrg = null)
    }

}

data class FormErrorStateBrg(
    val id: String? = null,
    val Nama: String? =null,
    val Deskripsi: String? =null,
    val Harga: String? =null,
    val Stok: String? =null,
    val NamaSuplier: String? =null,
){
    fun isValidBrg(): Boolean{
        return id == null && Nama == null && Deskripsi == null &&
                Harga == null && Stok  == null && NamaSuplier == null
    }
}
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

data class BrgUiState(
    val barangEvent: BarangEvent = BarangEvent(),
    val isEntryValidBrg: FormErrorStateBrg = FormErrorStateBrg(),
    val snackBarMessageBrg:String?=null,
)
