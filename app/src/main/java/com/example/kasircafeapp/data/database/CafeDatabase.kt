package com.example.kasircafeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kasircafeapp.data.dao.AdminDao
import com.example.kasircafeapp.data.dao.MakananDao
import com.example.kasircafeapp.data.dao.MinumanDao
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.data.entity.Minuman

@Database(entities = [Makanan::class, Minuman::class, Admin::class], version = 2, exportSchema = false)
abstract class CafeDatabase : RoomDatabase(){

    abstract fun makananDao(): MakananDao
    abstract fun minumanDao(): MinumanDao
    abstract fun adminDao(): AdminDao

    companion object{
        @Volatile
        private var Instance: CafeDatabase? = null

        fun getDatabase(context: Context) : CafeDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, CafeDatabase::class.java, "cafe_database")
                    .fallbackToDestructiveMigrationFrom(1)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
