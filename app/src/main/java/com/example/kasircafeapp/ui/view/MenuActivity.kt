package com.example.kasircafeapp.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Menu
import com.example.kasircafeapp.ui.adapter.MenuAdapter
import com.example.kasircafeapp.ui.viewmodel.MenuViewModel

class MenuActivity : AppCompatActivity() {

    private val menuViewModel: MenuViewModel by viewModels()
    private lateinit var adapter: MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        adapter = MenuAdapter()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMenu)
        recyclerView.adapter = adapter

        val manager = GridLayoutManager(this, 2)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position){
                    0,1,2 -> 1
                    else -> 1
//                    position % 3 == 0 -> 2
//                    else -> 1
                }
            }
        }
        recyclerView.layoutManager = manager



//        recyclerView.adapter = MenuAdapter()
//        recyclerView.adapter = adapter
//        recyclerView.layoutManager = LinearLayoutManager(this)

//        val gridLayoutManager = GridLayoutManager(this, 2)
//        recyclerView.layoutManager = gridLayoutManager
//        recyclerView.adapter = adapter

        menuViewModel.allMenuWithDetails.observe(this) { menuList ->
            if (menuList != null && menuList.isNotEmpty()) {
                adapter.submitList(menuList)
            }
        }

        menuViewModel.allMakanan.observe(this) { makananList ->
            makananList?.forEach { makanan ->
                val newMenu = Menu(
                    makanan_id = makanan.id_makanan,
                    minuman_id = null,
                    jumlah = 1,
                    totalHarga = makanan.harga
                )
                menuViewModel.insertIfNotExists(newMenu)
            }
        }

        menuViewModel.allMinuman.observe(this) { minumanList ->
            minumanList?.forEach { minuman ->
                val newMenu = Menu(
                    makanan_id = null,
                    minuman_id = minuman.id_minuman,
                    jumlah = 1,
                    totalHarga = minuman.harga_minuman
                )
                menuViewModel.insertIfNotExists(newMenu)
            }
        }
    }
}
