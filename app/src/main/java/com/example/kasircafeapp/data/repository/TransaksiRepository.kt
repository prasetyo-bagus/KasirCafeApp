package com.example.kasircafeapp.data.repository

import androidx.lifecycle.LiveData
import com.example.kasircafeapp.data.dao.TransaksiDao
import com.example.kasircafeapp.data.entity.Transaksi

class TransaksiRepository(private val transaksiDao: TransaksiDao) {

    fun getAllTransaksi(): LiveData<List<Transaksi>> = transaksiDao.getAllTransaksi()

    suspend fun insert(transaksi: Transaksi) {
        transaksiDao.insertTransaksi(transaksi)
    }
}