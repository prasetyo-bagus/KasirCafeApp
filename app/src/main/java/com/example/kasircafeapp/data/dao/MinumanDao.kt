package com.example.kasircafeapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kasircafeapp.data.entity.Minuman

@Dao
interface MinumanDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert_minuman(minuman : Minuman)

    @Update
    suspend fun update_minuman(minuman : Minuman)

    @Delete
    suspend fun delete_minuman(minuman : Minuman)

    @Query("SELECT * FROM minuman_table")
    fun getAllMinuman() : LiveData<List<Minuman>>

    @Query("SELECT * FROM minuman_table WHERE syncronize = 0")
    suspend fun getUnsyncedMinuman(): List<Minuman>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(minumanList: List<Minuman>)
}