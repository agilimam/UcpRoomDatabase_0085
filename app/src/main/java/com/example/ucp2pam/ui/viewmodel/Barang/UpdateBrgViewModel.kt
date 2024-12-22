package com.example.ucp2pam.ui.viewmodel.Barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Barang
import com.example.ucp2pam.data.repository.RepositoryBarang
import com.example.ucp2pam.ui.navigation.DestinasiUpdateBrg
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateBrgViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryBarang: RepositoryBarang
) : ViewModel() {
    var updateUiStateBrg by mutableStateOf(BrgUiState())
        private set

    private val _id: String = checkNotNull(savedStateHandle[DestinasiUpdateBrg.ID])

    init {
        viewModelScope.launch {
            updateUiStateBrg = repositoryBarang.getBarang(_id)
                .filterNotNull()
                .first()
                .toUiStateBrg()
        }

    }

    fun updateStateBrg(barangEvent: BarangEvent){
        updateUiStateBrg = updateUiStateBrg.copy(
           barangEvent = barangEvent
        )
    }

    fun validateFields() : Boolean {
        val event = updateUiStateBrg.barangEvent
        val errorStateBrg = FormErrorStateBrg(
            id = if (event.id.isNotEmpty()) null else "id tidak boleh kosong",
            Nama = if(event.Nama.isNotEmpty()) null else "NAMA tidak boleh kosong",
            Deskripsi = if (event.Deskripsi.isNotEmpty()) null else "Deskripsi tidak boleh kosong",
            Harga = if (event.Harga.isNotEmpty()) null else "Harga tidak boleh kosong",
            Stok = if (event.Stok.isNotEmpty()) null else "Stok tidak boleh kososng",
            NamaSuplier = if (event.NamaSuplier.isNotEmpty()) null else "NamaSuplier tidak boleh kosong",
        )
        updateUiStateBrg = updateUiStateBrg.copy(isEntryValidBrg = errorStateBrg)
        return errorStateBrg.isValidBrg()
    }

    fun updateDataBrg(){
        val currentEvent = updateUiStateBrg.barangEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryBarang.updateBarang(currentEvent.toBarangEntity())
                    updateUiStateBrg = updateUiStateBrg.copy(
                        snackBarMessageBrg = "Data berhasil di Update",
                        barangEvent = BarangEvent(),
                        isEntryValidBrg = FormErrorStateBrg()
                    )
                    println("snackBarMessage diatur : ${updateUiStateBrg.snackBarMessageBrg}")
                } catch (e: Exception){
                    updateUiStateBrg = updateUiStateBrg.copy(
                        snackBarMessageBrg = "Data gagal di update"
                    )
                }
            }
        } else {
            updateUiStateBrg = updateUiStateBrg.copy(
                snackBarMessageBrg = "Data gagal diupdate"
            )
        }
    }
    fun resetSnackBarMessaggeBrg() {
        updateUiStateBrg = updateUiStateBrg.copy(snackBarMessageBrg = null)
    }

}
fun Barang.toUiStateBrg():BrgUiState = BrgUiState(
    barangEvent = this.toDetailUiEvent(),
)