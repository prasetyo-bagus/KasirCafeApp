package com.example.kasircafeapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "minuman_table")
data class Minuman(
    @PrimaryKey(autoGenerate = true)
    val id_minuman: Int = 0,
    val nama_minuman: String,
    val harga_minuman: Int?,
    val kategori_minuman: String
)
