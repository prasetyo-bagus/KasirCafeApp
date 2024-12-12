package com.example.kasircafeapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "makanan_table")
data class Makanan(
    @PrimaryKey
    val id_makanan: String = "",
    val nama_makanan: String = "",
    val harga_makanan: Double = 0.0,
    val deskripsi_makanan: String = "",
    val kategori_makanan: String? = "",
    val syncronize: Boolean = false
)