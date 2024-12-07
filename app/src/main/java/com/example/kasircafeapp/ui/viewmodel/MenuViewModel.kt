package com.example.kasircafeapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.kasircafeapp.data.dao.TransaksiDao
import com.example.kasircafeapp.data.database.CafeDatabase
import com.example.kasircafeapp.data.entity.Transaksi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val transaksiDao: TransaksiDao = CafeDatabase.getDatabase(application).transaksiDao()

    // Fungsi untuk memasukkan transaksi ke database
    fun insertTransaksi(transaksi: Transaksi) {
        viewModelScope.launch(Dispatchers.IO) {
            transaksiDao.insertTransaksi(transaksi)
        }
    }
}