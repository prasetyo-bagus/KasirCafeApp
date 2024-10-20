package com.example.kasircafeapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.kasircafeapp.data.entity.Admin

@Dao
interface AdminDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(admin: Admin)

    @Update
    suspend fun update(admin: Admin)

    @Delete
    suspend fun delete(admin: Admin)

    @Query("SELECT * FROM admin_table WHERE username = :username AND password = :password")
    suspend fun getAdmin(username: String, password: String): Admin?

    @Query("SELECT * FROM admin_table")
    fun getAllAdmins(): LiveData<List<Admin>>

//    @Query("SELECT * FROM admin_table")
//    fun getAllAdmins(): LiveData<List<Admin>>
}