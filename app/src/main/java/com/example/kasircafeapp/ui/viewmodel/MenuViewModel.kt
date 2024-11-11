package com.example.kasircafeapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kasircafeapp.data.database.CafeDatabase
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.data.entity.Menu
import com.example.kasircafeapp.data.entity.Minuman
import com.example.kasircafeapp.data.relation.MenuWithDetails
import com.example.kasircafeapp.data.repository.MenuRepository
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MenuRepository
    val allMenuWithDetails: LiveData<List<MenuWithDetails>>
    val allMakanan: LiveData<List<Makanan>>
    val allMinuman: LiveData<List<Minuman>>

    private val _combinedMenuItems = MutableLiveData<List<Any>>()
    val combinedMenuItems: LiveData<List<Any>> get() = _combinedMenuItems

    init {
        val menuDao = CafeDatabase.getDatabase(application).menuDao()
        repository = MenuRepository(menuDao)
        allMenuWithDetails = repository.allMenuWithDetails
        allMakanan = repository.allMakanan
        allMinuman = repository.allMinuman
    }

    fun insert(menu: Menu) = viewModelScope.launch {
        repository.insert(menu)
    }

    fun insertIfNotExists(menu: Menu) = viewModelScope.launch {
        val existingMenu = repository.getMenuByMakananAndMinuman(menu.makanan_id, menu.minuman_id)
        if (existingMenu == null) {
            repository.insert(menu)
        }
    }
}