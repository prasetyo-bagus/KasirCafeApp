package com.example.kasircafeapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.data.entity.Minuman

@Dao
interface MakananDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert_makanan(makanan : Makanan)

    @Update
    suspend fun update_makanan(makanan : Makanan)

    @Delete
    suspend fun delete_makanan(makanan : Makanan)

    @Query("SELECT * FROM makanan_table")
    fun getAllMakanan() : LiveData<List<Makanan>>

    @Query("SELECT * FROM makanan_table WHERE syncronize = 0")
    suspend fun getUnsyncedMakanan(): List<Makanan>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(makananList: List<Makanan>)
}