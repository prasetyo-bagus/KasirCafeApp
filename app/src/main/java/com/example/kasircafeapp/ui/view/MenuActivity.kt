package com.example.kasircafeapp.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Menu
import com.example.kasircafeapp.data.entity.Minuman
import com.example.kasircafeapp.databinding.ActivityMenuBinding
import com.example.kasircafeapp.databinding.ItemMenuMinumanBinding
import com.example.kasircafeapp.ui.adapter.MakananAdapter
import com.example.kasircafeapp.ui.adapter.MenuAdapter
import com.example.kasircafeapp.ui.adapter.MinumanAdapter
import com.example.kasircafeapp.ui.viewmodel.MakananViewModel
import com.example.kasircafeapp.ui.viewmodel.MenuViewModel
import com.example.kasircafeapp.ui.viewmodel.MinumanViewModel

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding
    private lateinit var minumanViewModel: MinumanViewModel
    private lateinit var makananViewModel: MakananViewModel
    private lateinit var makananAdapter: MakananAdapter
    private lateinit var minumanAdapter: MinumanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Menu"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        minumanViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MinumanViewModel::class.java)
        makananViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MakananViewModel::class.java)

        val recyclerViewMakanan: RecyclerView = findViewById(R.id.recyclerViewMenuMakanan)
        makananAdapter = MakananAdapter(
            useLayoutMenuMakanan = true
        )
//            onDeleteClick =  (makanan) -> unit = {},
//            onEditClick = { makanan ->  },
//            onDetailClick = { makanan -> }

        recyclerViewMakanan.adapter = makananAdapter
        recyclerViewMakanan.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val recyclerViewMinuman: RecyclerView = findViewById(R.id.recyclerViewMenuMinuman)
        minumanAdapter = MinumanAdapter(object : MinumanAdapter.OnItemClickListener {
            override fun onItemClick(minuman: Minuman) {
                TODO("Not yet implemented")
            }
        }).apply {
            useLayoutMenuMinuman = true
        }
        recyclerViewMinuman.adapter = minumanAdapter
        recyclerViewMinuman.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

       // observe data makanan
        makananViewModel.allMakanan.observe(this) { makananList ->
            makananAdapter.setMakanan(makananList)
        }

        // observer data minuman
        minumanViewModel.allMinuman.observe(this) { minumanList ->
            minumanAdapter.submitList(minumanList)
        }











//        adapter = MenuAdapter()
//
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewMenu)
//        recyclerView.adapter = adapter
//
//        val manager = GridLayoutManager(this, 2)
//        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                return when (position){
//                    0,1,2 -> 1
//                    else -> 1
////                    position % 3 == 0 -> 2
////                    else -> 1
//                }
//            }
//        }
//        recyclerView.layoutManager = manager
//
//
//
////        recyclerView.adapter = MenuAdapter()
////        recyclerView.adapter = adapter
////        recyclerView.layoutManager = LinearLayoutManager(this)
//
////        val gridLayoutManager = GridLayoutManager(this, 2)
////        recyclerView.layoutManager = gridLayoutManager
////        recyclerView.adapter = adapter
//
//        menuViewModel.allMenuWithDetails.observe(this) { menuList ->
//            if (menuList != null && menuList.isNotEmpty()) {
//                adapter.submitList(menuList)
//            }
//        }
//
//        menuViewModel.allMakanan.observe(this) { makananList ->
//            makananList?.forEach { makanan ->
//                val newMenu = Menu(
//                    makanan_id = makanan.id_makanan,
//                    minuman_id = null,
//                    jumlah = 1,
//                    totalHarga = makanan.harga
//                )
//                menuViewModel.insertIfNotExists(newMenu)
//            }
//        }
//
//        menuViewModel.allMinuman.observe(this) { minumanList ->
//            minumanList?.forEach { minuman ->
//                val newMenu = Menu(
//                    makanan_id = null,
//                    minuman_id = minuman.id_minuman,
//                    jumlah = 1,
//                    totalHarga = minuman.harga_minuman
//                )
//                menuViewModel.insertIfNotExists(newMenu)
//            }
//        }
    }
}
