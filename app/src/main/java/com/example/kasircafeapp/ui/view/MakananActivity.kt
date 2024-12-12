package com.example.kasircafeapp.ui.view

import android.os.Bundle
import android.widget.Toast
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.databinding.ActivityMakananBinding
import com.example.kasircafeapp.ui.adapter.MakananAdapter
import com.example.kasircafeapp.ui.view.fragment.DetailMakananFragment
import com.example.kasircafeapp.ui.viewmodel.MakananViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MakananActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMakananBinding
    internal val makananViewModel: MakananViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMakananBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        observeViewModel()
        syncToFirebase()
        makananViewModel.syncMakanan()

        binding.fabAddMakanan.setOnClickListener {
            showTambahMakananFragment(null)
        }
    }
    private fun syncToFirebase() {
        val firebaseRef = FirebaseDatabase.getInstance().getReference("makanan")
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val makananList = mutableListOf<Makanan>()
                for (dataSnapshot in snapshot.children) {
                    val makanan = dataSnapshot.getValue(Makanan::class.java)
                    if (makanan != null) {
                        makananList.add(makanan)
                    }
                }
                makananViewModel.syncLocalDatabase(makananList) // Sinkron ke database lokal
                runOnUiThread {
                    (binding.recyclerViewMakanan.adapter as MakananAdapter).setMakanan(makananList)
                }
            }


            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Kelola Makanan"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        val adapter = MakananAdapter(
            onDeleteClick = { makanan -> makananViewModel.delete(makanan) },
            onEditClick = { makanan -> showTambahMakananFragment(makanan) },
            onDetailClick = { makanan -> showDetailMakananFragment(makanan) },
            useLayoutMenuMakanan = false
        )
        binding.recyclerViewMakanan.adapter = adapter
        binding.recyclerViewMakanan.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        makananViewModel.allMakanan.observe(this) { makananList ->
            makananList?.let { (binding.recyclerViewMakanan.adapter as MakananAdapter).setMakanan(it) }
        }
    }

    private fun showTambahMakananFragment(makanan: Makanan?) {
        val fragment = TambahMakananFragment().apply {
            setMakanan(makanan)
        }
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        binding.recyclerViewMakanan.visibility = View.GONE
        binding.fabAddMakanan.visibility = View.GONE
        binding.fragmentContainer.visibility = View.VISIBLE
    }

    private fun showDetailMakananFragment(makanan: Makanan) {
        val fragment = DetailMakananFragment().apply {
            arguments = Bundle().apply {
                putString("nama", makanan.nama_makanan)
                putDouble("harga", makanan.harga_makanan.toDouble())
                putString("deskripsi", makanan.deskripsi_makanan)
                putString("kategori", makanan.kategori_makanan)
            }
        }
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