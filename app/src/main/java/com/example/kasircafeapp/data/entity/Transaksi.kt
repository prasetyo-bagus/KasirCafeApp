package com.example.kasircafeapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "transaksi")
data class Transaksi(
    @PrimaryKey(autoGenerate = true)
    val id_transaksi: Int = 0,
    val namaPesanan: List<String>,
    val kategoriPesanan: String,
    val jumlahPesanan: Int,
    val jumlahBayar: Double,
    val totalHarga: Double,
    val tanggal: String
)

class Converters {
    // Mengonversi String menjadi List<String>
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    // Mengonversi List<String> menjadi String
    @TypeConverter
    fun fromList(value: List<String>): String {
        return value.joinToString(", ")  // Memisahkan list dengan koma
    }
}