package com.example.kasircafeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kasircafeapp.data.dao.MinumanDao
import com.example.kasircafeapp.data.entity.Minuman
import java.time.Instant

@Database(entities = [Minuman::class], version = 1, exportSchema = false)
abstract class CafeDatabase : RoomDatabase(){

    abstract fun minumanDao(): MinumanDao

    companion object{
        @Volatile
        private var Instance: CafeDatabase? = null

        fun getDatabase(context: Context) : CafeDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context, CafeDatabase::class.java, "cafe_database")
                    .fallbackToDestructiveMigrationFrom()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
