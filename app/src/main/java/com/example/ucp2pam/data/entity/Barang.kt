package com.example.ucp2pam.data.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Barang")
data class Barang(
    @PrimaryKey
    val id:String,
    val Nama:String,
    val Deskripsi:String,
    val Harga:String,
    val Stok:String,
    val NamaSuplier :String,
)
