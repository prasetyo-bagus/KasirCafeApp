package com.example.kasircafeapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.kasircafeapp.data.database.CafeDatabase
import com.example.kasircafeapp.data.entity.Makanan
import kotlinx.coroutines.launch

class MakananViewModel(application: Application) : AndroidViewModel(application) {

    private val makananDao = CafeDatabase.getDatabase(application).makananDao()
    val allMakanan: LiveData<List<Makanan>> = makananDao.getAllMakanan()

    fun insert(makanan: Makanan) = viewModelScope.launch {
        makananDao.insert(makanan)
    }

    fun delete(makanan: Makanan) = viewModelScope.launch {
        makananDao.delete(makanan)
    }

    fun update(makanan: Makanan) = viewModelScope.launch {
        makananDao.update(makanan)
    }
}