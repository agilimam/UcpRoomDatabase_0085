package com.example.ucp2pam.data.entity


import androidx.room.Entity

import androidx.room.PrimaryKey
@Entity(tableName = "Barang")
data class Barang(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    val Nama:String,
    val Deskripsi:String,
    val Harga:Double,
    val Stok:Int,
    val NamaSuplier :String,
)
