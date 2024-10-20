package com.example.kasircafeapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "makanan")
data class Makanan(
    @PrimaryKey(autoGenerate = true)
    val id_makanan: Int = 0,
    val nama: String,
    val harga: Double,
    val deskripsi: String,
    val kategori: String
)