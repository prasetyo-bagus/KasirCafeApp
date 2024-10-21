package com.example.kasircafeapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.kasircafeapp.data.dao.MakananDao
import com.example.kasircafeapp.data.database.CafeDatabase
import com.example.kasircafeapp.data.entity.Makanan
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TestingDatabase {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: CafeDatabase
    private lateinit var makananDao: MakananDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context, CafeDatabase::class.java).build()
        makananDao = database.makananDao()
    }

    @After
    @Throws(IOException::class)
    fun dbClose() = database.close()

    val makananList = listOf(
        Makanan(
            nama = "Nasi Goreng",
            harga = 15000.0,
            deskripsi = "Nasi goreng spesial dengan telur",
            kategori = "Makanan"
        ),
        Makanan(
            nama = "Mie Goreng",
            harga = 12000.0,
            deskripsi = "Mie goreng pedas",
            kategori = "Makanan"
        ),
        Makanan(
            nama = "Ayam Bakar",
            harga = 20000.0,
            deskripsi = "Ayam bakar dengan bumbu kecap",
            kategori = "Makanan"
        )
    )

    @Test
    @Throws(Exception::class)
    fun insertAndRetrieve() = runBlocking {
        // Insert all Makanan items
        makananList.forEach { makananDao.insert(it) }

        // Retrieve all Makanan items from the database using getOrAwaitValue()
        val allMakanan = makananDao.getAllMakanan().getOrAwaitValue()

        // Check if the number of retrieved items is correct
        Assert.assertEquals(3, allMakanan.size) // Perbaikan di sini

        // Print all Makanan items to the console
        allMakanan.forEach { makanan ->
            println("Makanan: $makanan")
        }
    }
}
