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
    val namaPesanan: List<String> = emptyList(),
    val jumlahPesanan: Int = 0,
    val jumlahBayar: Double = 0.0,
    val totalHarga: Double = 0.0,
    val nominalKembalian: Double = 0.0,
    val tanggal: String = "",
    val synchronize: Boolean = false
) : Parcelable
