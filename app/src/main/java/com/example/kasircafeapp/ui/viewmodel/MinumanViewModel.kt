package com.example.kasircafeapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import androidx.room.Delete
import androidx.room.Insert
import com.example.kasircafeapp.data.dao.MinumanDao
import com.example.kasircafeapp.data.database.CafeDatabase
import com.example.kasircafeapp.data.entity.Minuman
import kotlinx.coroutines.launch

class MinumanViewModel (application: Application) : AndroidViewModel(application){

    private val minumanDao = CafeDatabase.getDatabase(application).minumanDao()
    val allMinuman: LiveData<List<Minuman>> = minumanDao.getAllMinuman()

    fun insert(minuman: Minuman) = viewModelScope.launch {
        minumanDao.insert_minuman(minuman)
    }

    fun delete(minuman: Minuman) = viewModelScope.launch {
        minumanDao.delete_minuman(minuman)
    }

    fun update(minuman: Minuman) = viewModelScope.launch {
        minumanDao.update_minuman(minuman)
    }
}