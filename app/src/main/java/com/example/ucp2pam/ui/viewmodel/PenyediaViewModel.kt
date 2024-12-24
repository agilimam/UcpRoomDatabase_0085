package com.example.ucp2pam.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2pam.Apk
import com.example.ucp2pam.ui.viewmodel.Barang.DetailBrgViewModel
import com.example.ucp2pam.ui.viewmodel.Barang.HomeBrgViewModel
import com.example.ucp2pam.ui.viewmodel.Barang.InsertBrgViewModel
import com.example.ucp2pam.ui.viewmodel.Barang.UpdateBrgViewModel
import com.example.ucp2pam.ui.viewmodel.Suplier.HomeSplViewModel
import com.example.ucp2pam.ui.viewmodel.Suplier.InsertSplViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeBrgViewModel(
                Apk().containerApp.repositoryBarang

            )
        }
        initializer {
            InsertBrgViewModel(
                Apk().containerApp.repositoryBarang
            )
        }
        initializer {
            DetailBrgViewModel(
                createSavedStateHandle(),
                Apk().containerApp.repositoryBarang
            )
        }
        initializer {
            UpdateBrgViewModel(
                createSavedStateHandle(),
                Apk().containerApp.repositoryBarang
            )
        }
        initializer {
            HomeSplViewModel(
                Apk().containerApp.repositorySuplier
            )
        }
        initializer {
            InsertSplViewModel(
                Apk().containerApp.repositorySuplier
            )
        }

    }
}
fun CreationExtras.Apk():Apk=
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Apk)