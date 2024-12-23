package com.example.ucp2pam.repository

import com.example.ucp2pam.data.dao.SuplierDao
import com.example.ucp2pam.data.entity.Suplier
import kotlinx.coroutines.flow.Flow

class LocalRepositorySuplier (
    private val suplierDao: SuplierDao
) : RepositorySuplier {
    override suspend fun insertSuplier(suplier: Suplier) {
        suplierDao.insertSuplier(suplier)
    }

    override fun getAllSuplier(): Flow<List<Suplier>> {
        return suplierDao.getAllSuplier()
    }

}