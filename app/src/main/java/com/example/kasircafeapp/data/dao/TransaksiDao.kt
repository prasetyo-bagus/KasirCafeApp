package com.example.kasircafeapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kasircafeapp.data.entity.Transaksi

@Dao
interface TransaksiDao {
    @Query("SELECT * FROM transaksi")
    fun getAllTransaksi(): LiveData<List<Transaksi>>

    @Insert
    suspend fun insertTransaksi(transaksi: Transaksi)
}
