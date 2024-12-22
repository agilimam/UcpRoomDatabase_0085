package com.example.ucp2pam.ui.viewmodel.Barang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Barang
import com.example.ucp2pam.data.repository.RepositoryBarang
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeBrgViewModel(
    private val repositoryBarang: RepositoryBarang
) : ViewModel(){
    val homeBrgUiState : StateFlow<HomeBrgUiState> = repositoryBarang.getAllBarang()
        .filterNotNull()
        .map {
            HomeBrgUiState(
                listBrg = it.toList(),
                isLoadingBrg = false,
            )
        }
        .onStart {
            emit(HomeBrgUiState(isLoadingBrg = true))
            delay(900)
        }
        .catch {
            emit(
                HomeBrgUiState(
                    isLoadingBrg = false,
                    isErrorBrg = true,
                    errorMessageBrg = it.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeBrgUiState(
                isLoadingBrg = true,
            )
        )
}
data class HomeBrgUiState(
    val listBrg: List<Barang> = listOf(),
    val isLoadingBrg: Boolean =false,
    val isErrorBrg : Boolean = false,
    val errorMessageBrg : String = ""
)