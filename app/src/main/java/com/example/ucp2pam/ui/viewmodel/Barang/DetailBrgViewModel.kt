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
    private val _Nama: String = checkNotNull(savedStateHandle[DestinasiDetailBarang.Nama])

    val detailUiStateBrg: StateFlow<DetailUiStateBrg> = repositoryBarang.getBarang(_Nama)
        .filterNotNull()
        .map {
            DetailUiStateBrg(
                detailUiEventBrg = it.toDetailUiEventBrg(),
                isLoadingBrg = false,
            )
        }
        .onStart {
            emit(DetailUiStateBrg(isLoadingBrg = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiStateBrg(
                    isLoadingBrg = false,
                    isErrorBrg = true,
                    errorMessageBrg= it.message ?: "Terjadi Kesalahan",
                )

            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiStateBrg(
                isLoadingBrg = true,
            )
        )

    fun deleteBrg(){
        detailUiStateBrg.value.detailUiEventBrg.toBarangEntity().let {
            viewModelScope.launch {
               repositoryBarang.deleteBarang(it)
            }
        }
    }
}

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