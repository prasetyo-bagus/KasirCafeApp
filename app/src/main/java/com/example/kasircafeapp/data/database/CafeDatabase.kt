package com.example.kasircafeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kasircafeapp.data.dao.AdminDao
import com.example.kasircafeapp.data.dao.MakananDao
import com.example.kasircafeapp.data.dao.MenuDao
import com.example.kasircafeapp.data.dao.MinumanDao
import com.example.kasircafeapp.data.dao.TransaksiDao
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.data.entity.Menu
import com.example.kasircafeapp.data.entity.Minuman
import com.example.kasircafeapp.data.entity.NamaPesananConverter
import com.example.kasircafeapp.data.entity.Transaksi

@Database(entities = [Menu::class,Makanan::class, Minuman::class, Admin::class, Transaksi::class],
          version = 5,
          exportSchema = false)
@TypeConverters(NamaPesananConverter::class)
abstract class CafeDatabase : RoomDatabase(){

    abstract fun makananDao(): MakananDao
    abstract fun minumanDao(): MinumanDao
    abstract fun adminDao(): AdminDao
    abstract fun menuDao(): MenuDao
    abstract fun transaksiDao(): TransaksiDao

    companion object{
        @Volatile
        private var Instance: CafeDatabase? = null

        fun getDatabase(context: Context) : CafeDatabase{
            return Instance ?: synchronized(this){
                Room.databaseBuilder(context,
                    CafeDatabase::class.java,
                    "cafe_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
