package com.example.ucp2pam.repository

import com.example.ucp2pam.data.entity.Barang
import kotlinx.coroutines.flow.Flow

interface RepositoryBarang {
    suspend fun insertBarang(barang: Barang)

    //Dari sini
    fun getAllBarang(): Flow<List<Barang>>

    fun getBarang(id: Int) : Flow<Barang>

    suspend fun  deleteBarang(barang: Barang)

    suspend fun  updateBarang(barang: Barang)

}