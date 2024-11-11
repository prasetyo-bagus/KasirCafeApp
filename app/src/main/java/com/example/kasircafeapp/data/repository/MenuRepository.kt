package com.example.kasircafeapp.data.repository

import androidx.lifecycle.LiveData
import com.example.kasircafeapp.data.dao.MenuDao
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.data.entity.Menu
import com.example.kasircafeapp.data.entity.Minuman
import com.example.kasircafeapp.data.relation.MenuWithDetails

//class MenuRepository(private val menuDao: MenuDao){
//
//    val allMenuWithDetails: LiveData<List<MenuWithDetails>> = menuDao.getMenuWithDetails()
//
//    suspend fun insert(menu: Menu) {
//        menuDao.insert(menu)
//    }
//}

class MenuRepository(private val menuDao: MenuDao) {

    val allMenuWithDetails: LiveData<List<MenuWithDetails>> = menuDao.getAllMenuWithDetails()
    val allMakanan: LiveData<List<Makanan>> = menuDao.getAllMakanan()
    val allMinuman: LiveData<List<Minuman>> = menuDao.getAllMinuman()

    suspend fun insert(menu: Menu) {
        menuDao.insert(menu)
    }

    suspend fun getMenuByMakananAndMinuman(makananId: Int?, minumanId: Int?): Menu? {
        return menuDao.getMenuByMakananAndMinuman(makananId, minumanId)
    }
}
