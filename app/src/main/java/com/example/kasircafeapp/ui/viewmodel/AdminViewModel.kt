package com.example.kasircafeapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kasircafeapp.data.dao.AdminDao
import com.example.kasircafeapp.data.database.CafeDatabase
import com.example.kasircafeapp.data.entity.Admin
import kotlinx.coroutines.launch

class AdminViewModel(application: Application) : AndroidViewModel(application) {
    private val adminDao: AdminDao = CafeDatabase.getDatabase(application).adminDao()

    fun registerAdmin(admin: Admin) {
        viewModelScope.launch {
            adminDao.insert(admin)
        }
    }

    fun getAllAdmins(): LiveData<List<Admin>> { // Mengembalikan LiveData
        return adminDao.getAllAdmins()
    }

    suspend fun getAdmin(username: String, password: String): Admin? {
        return adminDao.getAdmin(username, password)
    }

    fun updateAdmin(admin: Admin) {
        viewModelScope.launch {
            adminDao.update(admin)
        }
    }

    fun deleteAdmin(admin: Admin) {
        viewModelScope.launch {
            adminDao.delete(admin)
        }
    }

}