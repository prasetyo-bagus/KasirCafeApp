package com.example.kasircafeapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin_table")
data class Admin(

    @PrimaryKey
    val id_admin: String = "",
    val username_admin: String = "",
    val password_admin: String = "",
    val email_admin: String = "",
    val syncronize: Boolean = false
)