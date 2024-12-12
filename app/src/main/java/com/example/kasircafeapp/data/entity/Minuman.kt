package com.example.kasircafeapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.IgnoreExtraProperties

@Entity(tableName = "minuman_table")
data class Minuman(
    @PrimaryKey
    val id_minuman: String = "",
    val nama_minuman: String = "",
    val harga_minuman: Double = 0.0,
    val kategori_minuman: String? = "",
    val syncronize: Boolean = false
)