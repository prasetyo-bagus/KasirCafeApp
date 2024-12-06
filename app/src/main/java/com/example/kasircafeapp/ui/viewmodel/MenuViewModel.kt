package com.example.kasircafeapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kasircafeapp.data.dao.TransaksiDao
import com.example.kasircafeapp.data.database.CafeDatabase
import com.example.kasircafeapp.data.entity.Transaksi
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val transaksiDao: TransaksiDao = CafeDatabase.getDatabase(application).transaksiDao()

    // Fungsi untuk memasukkan transaksi ke database
    fun insertTransaksi(transaksi: Transaksi) {
        viewModelScope.launch {
            transaksiDao.insertTransaksi(transaksi)
        }
    }
//
//    val totalHarga = MutableLiveData<Double>(0.0)
//    val jumlahPesanan = MutableLiveData<Int>(0)
//
//    fun tambahPesanan(harga: Double) {
//        jumlahPesanan.value = (jumlahPesanan.value ?: 0) + 1
//        totalHarga.value = (totalHarga.value ?: 0.0) + harga
//    }
//
//    fun kurangPesanan(harga: Double) {
//        if ((jumlahPesanan.value ?: 0) > 0) {
//            jumlahPesanan.value = (jumlahPesanan.value ?: 0) - 1
//            totalHarga.value = (totalHarga.value ?: 0.0) - harga
//        }
//    }
}