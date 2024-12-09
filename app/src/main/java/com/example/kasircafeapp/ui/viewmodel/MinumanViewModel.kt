package com.example.kasircafeapp.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kasircafeapp.data.database.CafeDatabase
import com.example.kasircafeapp.data.entity.Minuman
import com.example.kasircafeapp.data.network.NetworkHelper
import com.example.kasircafeapp.data.repository.MinumanRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MinumanViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MinumanRepository
    val allMinuman: LiveData<List<Minuman>>

    init {
        // Inisialisasi Repository, Database, Network Helper
        val database = CafeDatabase.getDatabase(application)
        val networkHelper = NetworkHelper(application)
        repository = MinumanRepository(
            minumanDao = database.minumanDao(),
            firebaseDatabase = FirebaseDatabase.getInstance(),
            networkHelper = networkHelper
        )
        allMinuman = repository.getAllMinuman()
    }

    fun insert(minuman: Minuman) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertMinuman(minuman)
    }

    fun update(minuman: Minuman) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateMinuman(minuman)
    }

    fun delete(minuman: Minuman) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteMinuman(minuman)
    }

    fun syncMinuman() = viewModelScope.launch(Dispatchers.IO) {
        repository.syncWithFirebase()
    }

    fun syncLocalDatabase(minumanList: List<Minuman>) = viewModelScope.launch(Dispatchers.IO) {
        repository.syncLocalDatabase(minumanList)
    }

    fun syncUnsyncData() {
        viewModelScope.launch {
            repository.syncUnsyncedData()
            withContext(Dispatchers.Main) {
                Toast.makeText(getApplication(), "Data offline berhasil disinkronkan!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}