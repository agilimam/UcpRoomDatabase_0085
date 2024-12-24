package com.example.ucp2pam.ui.viewmodel.Suplier

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Suplier
import com.example.ucp2pam.repository.RepositorySuplier
import com.example.ucp2pam.ui.viewmodel.Barang.FormErrorState
import kotlinx.coroutines.launch

class InsertSplViewModel (private val repositorySuplier: RepositorySuplier
) : ViewModel(){
    var uiState by mutableStateOf(SplUIState())

    fun updateState(suplierEvent: SuplierEvent){
        uiState = uiState.copy(
           suplierEvent = suplierEvent,
        )

    }private fun validataFields():Boolean{
        val event = uiState.suplierEvent
        val errorState = FormErrorStateSpl(
            Nama = if (event.Nama.isNotEmpty())null else " Nama tidak boleh kosong",
            Kontak = if (event.Kontak.isNotEmpty())null else "Kontak tidak boleh kosong",
            Alamat =  if (event.Alamat.isNotEmpty())null else " Alamat tidak boleh kosong",
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    fun saveData (){
        val currentEvent = uiState.suplierEvent

        if (validataFields()){
            viewModelScope.launch {
                try {
                    repositorySuplier.insertSuplier(currentEvent.toSuplierEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "data berhasil disimpan",
                        suplierEvent = SuplierEvent(),
                        isEntryValid = FormErrorStateSpl(),
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
data class FormErrorStateSpl(
    val Nama: String? =null,
    val Kontak: String? =null,
    val Alamat: String? =null,
){
    fun isValid(): Boolean{
        return  Nama == null && Kontak == null &&
                Alamat == null
    }
}
fun SuplierEvent.toSuplierEntity():Suplier = Suplier(
    id = id ?: 0,
    Nama = Nama,
    Kontak = Kontak,
    Alamat = Alamat,
)
data class SplUIState(
    val suplierEvent: SuplierEvent = SuplierEvent(),
    val isEntryValid: FormErrorStateSpl = FormErrorStateSpl(),
    val snackBarMessage:String?=null,
)

data class SuplierEvent(
    val id: Int? = null,
    val Nama: String = "",
    val Kontak: String = "",
    val Alamat: String = "",
)