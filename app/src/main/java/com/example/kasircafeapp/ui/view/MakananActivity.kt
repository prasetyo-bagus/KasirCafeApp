package com.example.kasircafeapp.ui.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.databinding.ActivityMakananBinding
import com.example.kasircafeapp.ui.adapter.MakananAdapter
import com.example.kasircafeapp.ui.viewmodel.MakananViewModel

class MakananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakananBinding
    internal val makananViewModel: MakananViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Makanan" // Title of the activity
            setDisplayHomeAsUpEnabled(true) // Show the back button
            setDisplayShowHomeEnabled(true)
        }

        // Handle toolbar back button click (similar to WhatsApp behavior)
        binding.toolbar.setNavigationOnClickListener {
            finish() // Close this activity and go back
        }

        val adapter = MakananAdapter(
            onDeleteClick = { makanan -> makananViewModel.delete(makanan) },
            onEditClick = { makanan -> showTambahMakananFragment(makanan) } // Panggil fungsi edit
        )
        binding.recyclerViewMakanan.adapter = adapter
        binding.recyclerViewMakanan.layoutManager = LinearLayoutManager(this)

        makananViewModel.allMakanan.observe(this) { makananList ->
            makananList?.let { adapter.setMakanan(it) }
        }

        // Tombol tambah makanan
        binding.fabAddMakanan.setOnClickListener {
            showTambahMakananFragment(null) // Null untuk mode tambah
        }
    }

    private fun showTambahMakananFragment(makanan: Makanan?) {
        val fragment = TambahMakananFragment.newInstance(makanan)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        binding.recyclerViewMakanan.visibility = View.GONE
        binding.fabAddMakanan.visibility = View.GONE
        binding.fragmentContainer.visibility = View.VISIBLE
    }

    fun hideTambahMakananFragment() {
        supportFragmentManager.popBackStack()
        binding.recyclerViewMakanan.visibility = View.VISIBLE
        binding.fabAddMakanan.visibility = View.VISIBLE
        binding.fragmentContainer.visibility = View.GONE
    }
}
