package com.example.kasircafeapp.data.repository

import androidx.lifecycle.LiveData
import com.example.kasircafeapp.data.dao.MakananDao
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.data.network.NetworkHelper
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MakananRepository(
    private val makananDao: MakananDao,
    private val firebaseDatabase: FirebaseDatabase,
    private val networkHelper: NetworkHelper
) {

    suspend fun insertMakanan(makanan: Makanan) = withContext(Dispatchers.IO) {
        val firebaseRef = firebaseDatabase.getReference("makanan").push()
        val generateId = firebaseRef.key ?: ""

        if (networkHelper.isNetworkConnected()) {
            val makananWithId = makanan.copy(id_makanan = generateId, syncronize = true) // Memperbarui status
            firebaseRef.setValue(makananWithId).await()
            makananDao.insert_makanan(makananWithId) // Simpan di database lokal
        } else {
            val makananWithId = makanan.copy(id_makanan = generateId)
            makananDao.insert_makanan(makananWithId)
        }

    }

    suspend fun updateMakanan(makanan: Makanan) = withContext(Dispatchers.IO) {
        if (networkHelper.isNetworkConnected()) {
            val firebaseRef = firebaseDatabase.getReference("makanan").child(makanan.id_makanan)
            firebaseRef.setValue(makanan).await()
        }
        makananDao.update_makanan(makanan)
    }

    suspend fun deleteMakanan(makanan: Makanan) = withContext(Dispatchers.IO) {
        if (networkHelper.isNetworkConnected()) {
            val firebaseRef = firebaseDatabase.getReference("makanan").child(makanan.id_makanan)
            firebaseRef.removeValue().await()
        }
        makananDao.delete_makanan(makanan)
    }

    fun getAllMakanan(): LiveData<List<Makanan>> = makananDao.getAllMakanan()

    suspend fun syncWithFirebase() = withContext(Dispatchers.IO) {
        if (networkHelper.isNetworkConnected()) {
            val firebaseRef = firebaseDatabase.getReference("makanan")
            val snapshot = firebaseRef.get().await()
            val firebaseMakananList = mutableListOf<Makanan>()
            snapshot.children.forEach {
                val makanan = it.getValue(Makanan::class.java)
                makanan?.let { firebaseMakananList.add(it) }
            }
            syncLocalDatabase(firebaseMakananList)
        }
    }

    suspend fun syncLocalDatabase(makananList: List<Makanan>) = withContext(Dispatchers.IO) {
        makananDao.insertAll(makananList)
    }

    suspend fun syncUnsyncedData() = withContext(Dispatchers.IO) {
        if (networkHelper.isNetworkConnected()) {
            val unsyncedMakanan = makananDao.getUnsyncedMakanan()
            unsyncedMakanan.forEach { makanan ->
                val firebaseRef = firebaseDatabase.getReference("makanan").child(makanan.id_makanan)
                    try {
                        firebaseRef.setValue(makanan).await() // Tunggu operasi selesai
                        makananDao.update_makanan(makanan.copy(syncronize = true)) // Sinkron status di lokal
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
            }
        }
    }
}