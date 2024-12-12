package com.example.kasircafeapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kasircafeapp.data.dao.TransaksiDao
import com.example.kasircafeapp.data.database.CafeDatabase
import com.example.kasircafeapp.data.entity.Minuman
import com.example.kasircafeapp.data.entity.Transaksi
import com.example.kasircafeapp.data.network.NetworkHelper
import com.example.kasircafeapp.data.repository.TransaksiRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {

//    private val transaksiDao: TransaksiDao = CafeDatabase.getDatabase(application).transaksiDao()

//    //     Fungsi untuk memasukkan transaksi ke database
//    fun insertTransaksi(transaksi: Transaksi) {
//        viewModelScope.launch(Dispatchers.IO) {
//            transaksiDao.insertTransaksi(transaksi)
//        }
//    }

    private val repository: TransaksiRepository
    val allTransaksi: LiveData<List<Transaksi>>
    init {
        val database = CafeDatabase.getDatabase(application)
        val networkHelper = NetworkHelper(application)
        repository = TransaksiRepository(
            transaksiDao = database.transaksiDao(),
            firebaseDatabase = FirebaseDatabase.getInstance(),
            networkHelper = networkHelper
        )
        allTransaksi = repository.getAllTransaksi()
    }

    fun insert(transaksi: Transaksi) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(transaksi)
    }

    fun syncUnsyncedData() = viewModelScope.launch {
        repository.syncUnsyncedData()
    }

    fun syncLocalDatabase(minumanList: List<Transaksi>) = viewModelScope.launch {
        repository.syncLocalDatabase(minumanList)
    }

    fun syncTransaksi() = viewModelScope.launch {
        repository.syncWithFirebase()
    }

}