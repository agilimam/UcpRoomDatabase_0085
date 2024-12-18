package com.example.ucp2pam.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ucp2pam.data.dao.BarangDao
import com.example.ucp2pam.data.dao.SuplierDao
import com.example.ucp2pam.data.entity.Barang
import com.example.ucp2pam.data.entity.Suplier

@Database(entities = [Barang::class,Suplier::class], version = 2, exportSchema = false )
abstract class AppDatabase : RoomDatabase() {

}