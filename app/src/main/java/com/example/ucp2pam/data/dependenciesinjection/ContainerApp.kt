package com.example.ucp2pam.data.dependenciesinjection

import android.content.Context
import com.example.ucp2pam.data.database.AppDatabase
import com.example.ucp2pam.repository.LocalRepositoryBarang
import com.example.ucp2pam.repository.LocalRepositorySuplier
import com.example.ucp2pam.repository.RepositoryBarang
import com.example.ucp2pam.repository.RepositorySuplier

interface InterfaceContainerApp {
    val repositoryBarang : RepositoryBarang
}

class ContainerApp (private val context: Context): InterfaceContainerApp {
    override val repositoryBarang: RepositoryBarang by lazy {
        LocalRepositoryBarang(AppDatabase.getDatabase(context).barangDao())

    }

    val repositorySuplier: RepositorySuplier by lazy {
        LocalRepositorySuplier(AppDatabase.getDatabase(context).supliarDao())
    }

}