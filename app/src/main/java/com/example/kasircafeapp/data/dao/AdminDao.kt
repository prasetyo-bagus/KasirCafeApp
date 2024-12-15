package com.example.kasircafeapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.data.entity.Makanan

@Dao
interface AdminDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert_admin(admin : Admin)

    @Update
    suspend fun update_admin(admin : Admin)

    @Delete
    suspend fun delete_admin(admin : Admin)

    @Query("SELECT * FROM admin_table")
    fun getAllAdmin() : LiveData<List<Admin>>

    @Query("SELECT * FROM admin_table WHERE syncronize = 0")
    suspend fun getUnsyncedAdmin(): List<Admin>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(adminList: List<Admin>)

    @Query("SELECT * FROM admin_table WHERE username_admin = :username AND password_admin = :password LIMIT 1")
    suspend fun getAdminByUsernameAndPassword(username: String, password: String): Admin?

}