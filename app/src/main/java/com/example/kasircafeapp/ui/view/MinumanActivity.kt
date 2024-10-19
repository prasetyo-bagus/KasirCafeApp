package com.example.kasircafeapp.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasircafeapp.data.entity.Minuman
import com.example.kasircafeapp.databinding.ActivityMinumanBinding
import com.example.kasircafeapp.ui.viewmodel.MinumanViewModel
import com.example.kasircafeapp.ui.adapter.MinumanAdapter

class MinumanActivity : AppCompatActivity(), MinumanAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMinumanBinding
    private val minumanViewModel: MinumanViewModel by viewModels()
    private lateinit var minumanAdapter: MinumanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMinumanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Minuman"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }


        minumanAdapter = MinumanAdapter(this)
        binding.recyclerViewMinuman.adapter = minumanAdapter
        binding.recyclerViewMinuman.layoutManager = LinearLayoutManager(this)


        minumanViewModel.allMinuman.observe(this) { minumanList ->
            minumanAdapter.submitList(minumanList)
        }


        binding.floatingbuttonadd.setOnClickListener {
            val namaMinuman = binding.inputtextminuman.editText?.text.toString()
            val hargaMinuman = binding.inputtextharga.editText?.text.toString().toIntOrNull()
            val kategoriMinuman = binding.inputtextkategoriminuman.editText?.text.toString()

            if (namaMinuman.isNotEmpty() && hargaMinuman != null) {
                val minuman = Minuman(nama_minuman = namaMinuman, harga_minuman = hargaMinuman, kategori_minuman = kategoriMinuman)
                minumanViewModel.insert(minuman)
                clearInputs()
            }
        }


        binding.floatingbuttondelete.setOnClickListener {
            val selectedMinuman = minumanAdapter.getSelectedMinuman()
            if (selectedMinuman != null) {
                minumanViewModel.delete(selectedMinuman)
                clearInputs()
            }
        }


        binding.floatingbuttonedit.setOnClickListener {
            val selectedMinuman = minumanAdapter.getSelectedMinuman()
            if (selectedMinuman != null) {
                val namaMinuman = binding.inputtextminuman.editText?.text.toString()
                val hargaMinuman = binding.inputtextharga.editText?.text.toString().toIntOrNull()
                val kategoriMinuman = binding.inputtextkategoriminuman.editText?.text.toString()

                if (namaMinuman.isNotEmpty() && hargaMinuman != null) {
                    val updatedMinuman = selectedMinuman.copy(nama_minuman = namaMinuman, harga_minuman = hargaMinuman, kategori_minuman = kategoriMinuman)
                    minumanViewModel.insert(updatedMinuman)
                    clearInputs()
                }
            }
        }
    }

    override fun onItemClick(minuman: Minuman) {
        binding.inputtextminuman.editText?.setText(minuman.nama_minuman)
        binding.inputtextharga.editText?.setText(minuman.harga_minuman.toString())
        binding.inputtextkategoriminuman.editText?.setText(minuman.kategori_minuman)
    }

    private fun clearInputs() {
        binding.inputtextminuman.editText?.text?.clear()
        binding.inputtextharga.editText?.text?.clear()
        binding.inputtextkategoriminuman.editText?.text?.clear()
    }
}
