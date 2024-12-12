package com.example.kasircafeapp.data.repository

import androidx.lifecycle.LiveData
import com.example.kasircafeapp.data.dao.TransaksiDao
import com.example.kasircafeapp.data.entity.Transaksi
import com.example.kasircafeapp.data.network.NetworkHelper
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class TransaksiRepository(
    private val transaksiDao: TransaksiDao,
    private val firebaseDatabase: FirebaseDatabase,
    private val networkHelper: NetworkHelper
) {

//    fun getAllTransaksi(): LiveData<List<Transaksi>> = transaksiDao.getAllTransaksi()
//
//    suspend fun insert(transaksi: Transaksi) {
//        transaksiDao.insertTransaksi(transaksi)
//    }
    fun getAllTransaksi(): LiveData<List<Transaksi>> = transaksiDao.getAllTransaksi()

    suspend fun insert(transaksi: Transaksi) {
        withContext(Dispatchers.IO) {
            val firebaseRef = firebaseDatabase.getReference("transaksi").push()
            val generatedId = firebaseRef.key ?: ""

            if (networkHelper.isNetworkConnected()) {
                val transaksiWithId = transaksi.copy(id_transaksi = generatedId, synchronize = true)
                firebaseRef.setValue(transaksiWithId).await()
                transaksiDao.insertTransaksi(transaksiWithId)
            } else {
                val offlineTransaksi = transaksi.copy(id_transaksi = generatedId)
                transaksiDao.insertTransaksi(offlineTransaksi)
            }
        }
    }
    suspend fun syncUnsyncedData() {
        if (networkHelper.isNetworkConnected()) {
            withContext(Dispatchers.IO) {
                val unsyncedTransaksi = transaksiDao.getUnsyncedTransaksi()
                unsyncedTransaksi.forEach { transaksi ->
                    val firebaseRef = firebaseDatabase.getReference("transaksi").child(transaksi.id_transaksi)
                    firebaseRef.setValue(transaksi).await()
                    transaksiDao.insertTransaksi(transaksi.copy(synchronize = true))
                }
            }
        }
    }

    suspend fun syncWithFirebase() {
        if (networkHelper.isNetworkConnected()) {
            withContext(Dispatchers.IO) {
                val firebaseRef = firebaseDatabase.getReference("transaksi")
                val snapshot = firebaseRef.get().await()
                val firebaseTransaksiList = mutableListOf<Transaksi>()
                snapshot.children.forEach {
                    val transaksi = it.getValue(Transaksi::class.java)
                    transaksi?.let { firebaseTransaksiList.add(it) }
                }
                syncLocalDatabase(firebaseTransaksiList)
            }
        }
    }

    suspend fun syncLocalDatabase(transaksiList: List<Transaksi>) {
        withContext(Dispatchers.IO) {
            transaksiList.forEach { transaksiDao.insertTransaksi(it) }
        }
    }
}