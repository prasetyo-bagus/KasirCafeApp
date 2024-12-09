package com.example.kasircafeapp.data.repository

import androidx.lifecycle.LiveData
import com.example.kasircafeapp.data.dao.MinumanDao
import com.example.kasircafeapp.data.entity.Minuman
import com.example.kasircafeapp.data.network.NetworkHelper
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MinumanRepository(
    private val minumanDao: MinumanDao,
    private val firebaseDatabase: FirebaseDatabase,
    private val networkHelper: NetworkHelper
) {

    // Insert Minuman Ke Firebase Jika Tersambung Internet | Ke Sqlite Jika Offline
    suspend fun insertMinuman(minuman: Minuman) {
        withContext(Dispatchers.IO) {

            val firebaseRef = firebaseDatabase.getReference("minuman").push()
            val generateId = firebaseRef.key ?: ""

            if (networkHelper.isNetworkConnected()) {
//                val firebaseRef = firebaseDatabase.getReference("minuman").push()
//                val generateId = firebaseRef.key ?: ""

                val minumanWithId = minuman.copy(id_minuman = generateId)
                firebaseRef.setValue(minumanWithId).await()

                minumanDao.update_minuman(minumanWithId.copy(syncronize = true))
            } else {
//                val buatIdMinumanSementara = minuman.copy(id_minuman = "offlineId${System.currentTimeMillis()}")
                val buatIdMinuman = minuman.copy(id_minuman = generateId)
                minumanDao.insert_minuman(buatIdMinuman)
            }
        }
    }

    // Memperbarui data
    suspend fun updateMinuman(minuman: Minuman) {
        withContext(Dispatchers.IO) {
            if (networkHelper.isNetworkConnected()) {
                val firebaseRef = firebaseDatabase.getReference("minuman").child(minuman.id_minuman)
                firebaseRef.setValue(minuman).await()
            }
            minumanDao.update_minuman(minuman)
        }
    }

    // Menghapus data
    suspend fun deleteMinuman(minuman: Minuman) {
        withContext(Dispatchers.IO) {
            if (networkHelper.isNetworkConnected()) {
                val firebaseRef = firebaseDatabase.getReference("minuman").child(minuman.id_minuman)
                firebaseRef.removeValue().await()
            }
            minumanDao.delete_minuman(minuman)
        }
    }

    // Mendapatkan semua data dari database lokal
    fun getAllMinuman(): LiveData<List<Minuman>> = minumanDao.getAllMinuman()

    // Sinkronisasi data Firebase dengan database lokal
    suspend fun syncWithFirebase() {
        if (networkHelper.isNetworkConnected()) {
            withContext(Dispatchers.IO) {
                val firebaseRef = firebaseDatabase.getReference("minuman")
                val snapshot = firebaseRef.get().await()
                val firebaseMinumanList = mutableListOf<Minuman>()
                snapshot.children.forEach {
                    val minuman = it.getValue(Minuman::class.java)
                    minuman?.let { firebaseMinumanList.add(it) }
                }
                syncLocalDatabase(firebaseMinumanList)
            }
        }
    }

//    Sinkronisasi data lokal dengan data dari Firebase
    suspend fun syncLocalDatabase(minumanList: List<Minuman>) {
        withContext(Dispatchers.IO) {
            minumanDao.insertAll(minumanList)
        }
    }

    // Sinkronisasi data yang belum tersinkronisasi dari lokal ke Firebase
    suspend fun syncUnsyncedData() {
        if (networkHelper.isNetworkConnected()) {
            withContext(Dispatchers.IO) {
                val unsyncedMinuman = minumanDao.getUnsyncedMinuman()
                unsyncedMinuman.forEach { minuman ->
                    val firebaseRef = firebaseDatabase.getReference("minuman").child(minuman.id_minuman)
                    firebaseRef.setValue(minuman).await()
                    minumanDao.update_minuman(minuman.copy(syncronize = true))
                }
            }
        }
    }
}
