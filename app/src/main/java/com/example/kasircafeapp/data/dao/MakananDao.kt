package com.example.kasircafeapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kasircafeapp.data.entity.Makanan

@Dao
interface MakananDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(makanan: Makanan)

    @Update
    suspend fun update(makanan: Makanan)

    @Delete
    suspend fun delete(makanan: Makanan)

    @Query("SELECT * FROM makanan")
    fun getAllMakanan(): LiveData<List<Makanan>>
}