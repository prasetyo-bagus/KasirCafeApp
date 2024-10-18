package com.example.kasircafeapp.data.entity

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Parcelize
@Entity(tableName = "makanan")
data class Makanan(
    @PrimaryKey(autoGenerate = true)
    val id_makanan: Int = 0,
    val nama: String,
    val harga: Double,
    val deskripsi: String,
    val kategori: String
) : Parcelable
