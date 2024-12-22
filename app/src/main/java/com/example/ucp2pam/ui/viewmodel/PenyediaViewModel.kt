package com.example.ucp2pam.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2pam.Apk
import com.example.ucp2pam.ui.viewmodel.Barang.HomeBrgViewModel
import com.example.ucp2pam.ui.viewmodel.Barang.InsertBrgViewModel

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
    }
}
fun CreationExtras.Apk():Apk=
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Apk)