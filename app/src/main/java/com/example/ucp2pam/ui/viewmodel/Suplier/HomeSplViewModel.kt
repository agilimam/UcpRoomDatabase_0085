package com.example.ucp2pam.ui.viewmodel.Suplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2pam.data.entity.Suplier
import com.example.ucp2pam.repository.RepositorySuplier
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class HomeSplViewModel (
    private val  repositorySuplier: RepositorySuplier

) : ViewModel() {
    val homeUIState : StateFlow<HomeUIState> = repositorySuplier.getAllSuplier()
        .filterNotNull()
        .map {
            HomeUIState(
                listSpl = it.toList(),
                isLoading = false,
            )
        }
        .onStart {
            emit(HomeUIState(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUIState(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUIState(
                isLoading = true,
            )
        )
}

data class HomeUIState(
    val listSpl: List<Suplier> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)