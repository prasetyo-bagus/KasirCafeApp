package com.example.kasircafeapp.data.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "transaksi")
@TypeConverters(NamaPesananConverter::class)
@Parcelize
data class Transaksi(
    @PrimaryKey() val id_transaksi: String = "",
    val namaPesanan: List<String>,
    val jumlahPesanan: Int,
    val jumlahBayar: Double,
    val totalHarga: Double,
    val nominalKembalian: Double,
    val tanggal: String,
    val synchronize: Boolean = false
) : Parcelable
