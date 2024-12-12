package com.example.kasircafeapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kasircafeapp.data.database.CafeDatabase
import com.example.kasircafeapp.data.entity.NamaPesananConverter
import com.example.kasircafeapp.data.entity.Transaksi
import com.example.kasircafeapp.data.repository.TransaksiRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TransaksiViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository: TransaksiRepository
//    val allTransaksi: LiveData<List<Transaksi>>

//    init {
//        val transaksiDao = CafeDatabase.getDatabase(application).transaksiDao()
//        repository = TransaksiRepository(transaksiDao)
//        allTransaksi = repository.getAllTransaksi()
//    }
//
//    fun insert(transaksi: Transaksi) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.insert(transaksi)
//        }
//    }
}