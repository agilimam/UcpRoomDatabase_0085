package com.example.ucp2pam.ui.viewmodel.Barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Barang
import com.example.ucp2pam.repository.RepositoryBarang
import kotlinx.coroutines.launch

class InsertBrgViewModel (private val repositoryBarang: RepositoryBarang) : ViewModel(){
    var uiState by mutableStateOf(BrgUIState())

    fun updateState(barangEvent: BarangEvent){
        uiState = uiState.copy(
            barangEvent = barangEvent,
        )

    }private fun validataFields():Boolean{
        val event = uiState.barangEvent
        val errorState = FormErrorState(
            Nama = if (event.Nama.isNotEmpty())null else " Nama tidak boleh kosong",
            Deskripsi = if (event.Deskripsi.isNotEmpty())null else " Deskripsi tidak boleh kosong",
            Harga = if (event.Harga.isNotEmpty())null else " harga tidak boleh kosong",
            Stok = if (event.Stok.isNotEmpty())null else " Stok tidak boleh kosong",
            NamaSuplier = if (event.NamaSuplier.isNotEmpty())null else " Namasuplier tidak boleh kosong",
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    fun saveData (){
        val currentEvent = uiState.barangEvent

        if (validataFields()){
            viewModelScope.launch {
                try {
                    repositoryBarang.insertBarang(currentEvent.toBarangEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "data berhasil disimpan",
                        barangEvent = BarangEvent(),
                        isEntryValid = FormErrorState(),
                    )
                }catch (e: Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "data gagal disimpan"
                    )
                }
            }
        }else {
            uiState = uiState.copy(
                snackBarMessage = "input tidak valid periksa data kembali"
            )
        }
    }
    fun resetSnackBarMessage(){
        uiState= uiState.copy(snackBarMessage = null)
    }

}

data class FormErrorState(
    val Nama: String? =null,
    val Deskripsi: String? =null,
    val Harga: String? =null,
    val Stok: String? =null,
    val NamaSuplier: String? =null,
){
    fun isValid(): Boolean{
        return  Nama == null && Deskripsi == null &&
                Harga == null && Stok  == null && NamaSuplier == null
    }
}
fun BarangEvent.toBarangEntity():Barang = Barang(
    id = id ?: 0,
    Nama = Nama,
    Deskripsi = Deskripsi,
    Harga = Harga.toDoubleOrNull() ?: 0.0,
    Stok = Stok.toIntOrNull() ?: 0,
    NamaSuplier = NamaSuplier
)
data class BarangEvent(
    val id: Int? = null,
    val Nama: String = "",
    val Deskripsi: String = "",
    val Harga: String = "",
    val Stok: String = "",
    val NamaSuplier: String = "",
)

data class BrgUIState(
    val barangEvent: BarangEvent = BarangEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage:String?=null,
)
