package com.example.kasircafeapp.data.entity

import androidx.room.TypeConverter

class NamaPesananConverter {
    @TypeConverter
    fun stringToList(value: String): List<String> {
        return value.split(", ").map { it.trim( )}
    }

    @TypeConverter
    fun listToString(value: List<String>): String {
        return value.joinToString(", ")
    }
}