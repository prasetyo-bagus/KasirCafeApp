package com.example.kasircafeapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin_table")
data class Admin(
    @PrimaryKey(autoGenerate = true)
    val id_admin: Int = 0,
    val username: String,
    val password: String,
    val email: String
)
