package com.example.ucp2pam.ui.viewmodel.Barang

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Barang
import com.example.ucp2pam.data.repository.RepositoryBarang
import com.example.ucp2pam.ui.navigation.DestinasiDetailBarang
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class DetailBrgViewModel (
    savedStateHandle: SavedStateHandle,
    private val repositoryBarang: RepositoryBarang

    ) : ViewModel() {
    private val _id: String = checkNotNull(savedStateHandle[DestinasiDetailBarang.ID])

    val detailUiState: StateFlow<DetailUiState> = repositoryBarang.getBarang(_id)
        .filterNotNull()
        .map {
            DetailUiState(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false,
            )
        }
        .onStart {
            emit(DetailUiState(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan",
                )

            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiState(
                isLoading = true,
            )
        )

    fun deleteBrg(){
        detailUiState.value.detailUiEvent.toBarangEntity().let {
            viewModelScope.launch {
              repositoryBarang.deleteBarang(it)
            }
        }
    }

}


data class DetailUiState(
    val detailUiEvent: BarangEvent = BarangEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""

) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == BarangEvent()
    val isUiEvenNotEmpty: Boolean
        get() = detailUiEvent != BarangEvent()
}

//Data class untuk menampung data yang akan di tampilkan di UI

// Memindahkan data dari entity ke ui

fun Barang.toDetailUiEvent(): BarangEvent{
    return BarangEvent(
        id = id,
        Nama = Nama,
        Deskripsi = Deskripsi,
        Harga = Harga,
        Stok = Stok,
        NamaSuplier = NamaSuplier,
    )
}