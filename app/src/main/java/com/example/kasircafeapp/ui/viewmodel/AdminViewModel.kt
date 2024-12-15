package com.example.kasircafeapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kasircafeapp.data.database.CafeDatabase
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.data.network.NetworkHelper
import com.example.kasircafeapp.data.repository.AdminRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AdminRepository
    val allAdmins: LiveData<List<Admin>>

    init {
        val database = CafeDatabase.getDatabase(application)
        val networkHelper = NetworkHelper(application)
        repository = AdminRepository(
            adminDao = database.adminDao(),
            firebaseDatabase = FirebaseDatabase.getInstance(),
            networkHelper = networkHelper
        )
        allAdmins = repository.getAllAdmin()
    }

    fun insert(admin: Admin) = viewModelScope.launch {
        repository.insert_admin(admin)
    }

    fun update(admin: Admin) = viewModelScope.launch {
        repository.update_admin(admin)
    }

    fun delete(admin: Admin) = viewModelScope.launch {
        repository.delete_admin(admin)
    }

    fun syncLocalDatabase(adminList: List<Admin>) = viewModelScope.launch(Dispatchers.IO) {
        repository.syncLocalDatabase(adminList)
    }

    suspend fun getAdminByUsernameAndPassword(username: String, password: String): Admin? {
        return repository.getAdminByUsernameAndPassword(username, password)
    }

    suspend fun getAdmin(username: String, password: String): Admin? {
        return repository.getAdminByUsernameAndPassword(username, password)
    }


}
