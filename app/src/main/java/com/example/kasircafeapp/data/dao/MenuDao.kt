package com.example.kasircafeapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.data.entity.Menu
import com.example.kasircafeapp.data.entity.Minuman

@Dao
interface MenuDao {

    @Query("SELECT * FROM menu_table WHERE makanan_id = :makananId OR minuman_id = :minumanId")
    suspend fun getMenuByMakananAndMinuman(makananId: Int?, minumanId: Int?): Menu?

    @Query("SELECT * FROM makanan_table")
    fun getAllMakanan(): LiveData<List<Makanan>>

    @Query("SELECT * FROM minuman_table")
    fun getAllMinuman(): LiveData<List<Minuman>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(menu: Menu)
}
