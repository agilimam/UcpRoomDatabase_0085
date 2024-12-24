package com.example.ucp2pam.ui.viewmodel.Barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Barang
import com.example.ucp2pam.repository.RepositoryBarang
import com.example.ucp2pam.ui.navigation.DestinasiUpdateBrg
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateBrgViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryBarang: RepositoryBarang
) : ViewModel() {
    var updateUiState by mutableStateOf(BrgUIState())
        private set

    private val _id: Int = checkNotNull(savedStateHandle[DestinasiUpdateBrg.ID])

    init {
        viewModelScope.launch {
            updateUiState = repositoryBarang.getBarang(_id)
                .filterNotNull()
                .first()
                .toUIStateBrg()
        }

    }

    fun updateState(barangEvent: BarangEvent){
        updateUiState = updateUiState.copy(
            barangEvent = barangEvent,
        )
    }

    fun validateFields() : Boolean {
        val event = updateUiState.barangEvent
        val errorState = FormErrorState(
            Nama = if(event.Nama.isNotEmpty()) null else "NAMA tidak boleh kosong",
            Deskripsi = if (event.Deskripsi.isNotEmpty()) null else "Deskripsi tidak boleh kosong",
            Harga = if (event.Harga.isNotEmpty()) null else "Harga tidak boleh kosong",
            Stok = if (event.Stok.isNotEmpty()) null else "Stok tidak boleh kososng",
            NamaSuplier = if (event.NamaSuplier.isNotEmpty()) null else "NamaSupliertidak boleh kosong",
        )
        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData(){
        val currentEvent = updateUiState.barangEvent
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryBarang.updateBarang(currentEvent.toBarangEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data berhasil di Update",
                        barangEvent = BarangEvent(),
                        isEntryValid = FormErrorState()
                    )
                    println("snackBarMessage diatur : ${updateUiState.snackBarMessage}")
                } catch (e: Exception){
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data gagal di update"
                    )
                }
            }
        } else {
            updateUiState = updateUiState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }
    fun resetSnackBarMessage() {
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }

}
fun Barang.toUIStateBrg():BrgUIState = BrgUIState(
    barangEvent = this.toDetailUiEvent(),

    )