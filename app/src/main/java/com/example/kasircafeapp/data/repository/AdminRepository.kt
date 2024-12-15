package com.example.kasircafeapp.data.repository

import androidx.lifecycle.LiveData
import com.example.kasircafeapp.data.dao.AdminDao
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.data.network.NetworkHelper
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AdminRepository(
    private val adminDao: AdminDao,
    private val firebaseDatabase: FirebaseDatabase,
    private val networkHelper: NetworkHelper
) {

    suspend fun insert_admin(admin: Admin) = withContext(Dispatchers.IO) {
        val firebaseRef = firebaseDatabase.getReference("admin").push()
        val generatedId = firebaseRef.key ?: ""

        val adminWithId = admin.copy(id_admin = generatedId)
        if (networkHelper.isNetworkConnected()) {
            firebaseRef.setValue(adminWithId).await()
            adminDao.insert_admin(adminWithId)
        } else {
            adminDao.insert_admin(adminWithId)
        }
    }

    suspend fun update_admin(admin: Admin) = withContext(Dispatchers.IO) {
        if (networkHelper.isNetworkConnected()) {
            val firebaseRef = firebaseDatabase.getReference("admin").child(admin.id_admin)
            firebaseRef.setValue(admin).await()
        }
        adminDao.update_admin(admin)
    }

    suspend fun syncLocalDatabase(adminList: List<Admin>) = withContext(Dispatchers.IO) {
        adminDao.insertAll(adminList)
    }

    suspend fun delete_admin(admin: Admin) = withContext(Dispatchers.IO) {
        if (networkHelper.isNetworkConnected()) {
            val firebaseRef = firebaseDatabase.getReference("admin").child(admin.id_admin)
            firebaseRef.removeValue().await()
        }
        adminDao.delete_admin(admin)
    }

    fun getAllAdmin(): LiveData<List<Admin>> = adminDao.getAllAdmin()

    suspend fun getAdminByUsernameAndPassword(username: String, password: String): Admin? {
        return adminDao.getAdminByUsernameAndPassword(username, password)
    }


}
