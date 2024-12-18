package com.example.ucp2pam.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Suplier")
data class Suplier(
    @PrimaryKey
    val id:String,
    val Nama:String,
    val Kontak:String,
    val Alamat:String,
)
