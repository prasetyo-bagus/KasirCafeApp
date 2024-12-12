package com.example.kasircafeapp.ui.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kasircafeapp.data.database.CafeDatabase
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.data.network.NetworkHelper
import com.example.kasircafeapp.data.repository.MakananRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MakananViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MakananRepository
    val allMakanan: LiveData<List<Makanan>>

    init {
        val database = CafeDatabase.getDatabase(application)
        val networkHelper = NetworkHelper(application)
        repository = MakananRepository(
            makananDao = database.makananDao(),
            firebaseDatabase = FirebaseDatabase.getInstance(),
            networkHelper = networkHelper
        )
        allMakanan = repository.getAllMakanan()
    }

    fun insert(makanan: Makanan) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertMakanan(makanan)
    }

    fun update(makanan: Makanan) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateMakanan(makanan)
    }

    fun delete(makanan: Makanan) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteMakanan(makanan)
    }

    fun syncMakanan() = viewModelScope.launch(Dispatchers.IO) {
        repository.syncWithFirebase()
    }

    fun syncLocalDatabase(makananList: List<Makanan>) = viewModelScope.launch(Dispatchers.IO) {
        repository.syncLocalDatabase(makananList)
    }

    fun syncUnsyncedData() = viewModelScope.launch {
        repository.syncUnsyncedData()
        withContext(Dispatchers.Main) {
            Toast.makeText(getApplication(), "Data offline berhasil disinkronkan!", Toast.LENGTH_SHORT).show()
        }
    }
}