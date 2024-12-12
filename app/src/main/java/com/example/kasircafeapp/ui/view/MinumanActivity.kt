package com.example.kasircafeapp.ui.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasircafeapp.data.entity.Minuman
import com.example.kasircafeapp.databinding.ActivityMinumanBinding
import com.example.kasircafeapp.ui.viewmodel.MinumanViewModel
import com.example.kasircafeapp.ui.adapter.MinumanAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MinumanActivity : AppCompatActivity(), MinumanAdapter.OnItemClickListener, MinumanAdapter.onDataChangedListener  {

    private lateinit var binding: ActivityMinumanBinding
    private val minumanViewModel: MinumanViewModel by viewModels()
    private lateinit var minumanAdapter: MinumanAdapter
    private var useLayoutMenuMinuman: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMinumanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Kelola Minuman"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        minumanAdapter = MinumanAdapter(this, this)
        binding.recyclerViewMinuman.adapter = minumanAdapter
        binding.recyclerViewMinuman.layoutManager = LinearLayoutManager(this)

        minumanViewModel.allMinuman.observe(this) { minumanList ->
            minumanAdapter.submitList(minumanList)
        }

        syncToFirebase()
        minumanViewModel.syncMinuman()

        binding.floatingbuttonadd.setOnClickListener {
            val namaMinuman = binding.inputtextminuman.editText?.text.toString()
            val hargaMinuman = binding.inputtextharga.editText?.text.toString().toIntOrNull()
            val kategoriMinuman = binding.inputtextkategoriminuman.editText?.text.toString()

            if (namaMinuman.isNotEmpty() && hargaMinuman != null) {
                val minuman = Minuman(nama_minuman = namaMinuman, harga_minuman = hargaMinuman.toDouble(), kategori_minuman = kategoriMinuman)
                minumanViewModel.insert(minuman)
                showToast("Data berhasil disimpan")
                clearInputs()
            }
        }

        binding.floatingbuttondelete.setOnClickListener {
            val selectedMinuman = minumanAdapter.getSelectedMinuman()
            if (selectedMinuman != null) {
                AlertDialog.Builder(this).apply {
                    setTitle("Hapus Minuman")
                    setMessage("Anda yakin ingin menghapus minuman ini?")
                    setPositiveButton("Ya") { _, _ ->
                        minumanViewModel.delete(selectedMinuman)
                        clearInputs()
                    }
                    setNegativeButton("Batal", null)
                }.show()
            } else {
                showToast("Pilih minuman yang ingin dihapus")
            }
        }

        binding.floatingbuttonedit.setOnClickListener {
            val selectedMinuman = minumanAdapter.getSelectedMinuman()
            if (selectedMinuman != null) {
                val namaMinuman = binding.inputtextminuman.editText?.text.toString()
                val hargaMinuman = binding.inputtextharga.editText?.text.toString().toDoubleOrNull()
                val kategoriMinuman = binding.inputtextkategoriminuman.editText?.text.toString()

                if (namaMinuman.isNotEmpty() && hargaMinuman != null) {
                    val updatedMinuman = selectedMinuman.copy(
                        nama_minuman = namaMinuman,
                        harga_minuman = hargaMinuman,
                        kategori_minuman = kategoriMinuman
                    )
                    showToast("Data Berhasil Di Edit!")
                    minumanViewModel.update(updatedMinuman)
                    clearInputs()
                } else {
                    showToast("Lengkapi data minuman")
                }
            } else {
                showToast("Pilih minuman yang ingin diedit")
            }
        }
    }

    private fun syncToFirebase() {
        val firebaseRef = FirebaseDatabase.getInstance().getReference("minuman")
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val minumanList = mutableListOf<Minuman>()

                for (dataSnapshot in snapshot.children) {
                    val minuman = dataSnapshot.getValue(Minuman::class.java)
                    if (minuman != null) {
                        minumanList.add(minuman)
                    }
                }

                minumanAdapter.submitList(minumanList)
                minumanViewModel.syncLocalDatabase(minumanList)
                minumanViewModel.syncUnsyncData()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

    override fun onDataChanged(totalHarga: Double, jumlahPesanan: Int, namaMinumanList: List<String>) {
    // fungsi ini hanya digunakan pada activity menu untuk mengambil total harga
    }
}