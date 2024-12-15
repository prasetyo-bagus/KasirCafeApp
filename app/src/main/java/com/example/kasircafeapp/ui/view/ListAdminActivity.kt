package com.example.kasircafeapp.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.databinding.ActivityListAdminBinding
import com.example.kasircafeapp.ui.adapter.AdminAdapter
import com.example.kasircafeapp.ui.adapter.MakananAdapter
import com.example.kasircafeapp.ui.viewmodel.AdminViewModel
import com.example.kasircafeapp.ui.view.fragment.FormAdminFragment
import com.example.kasircafeapp.ui.view.fragment.RegisterFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ListAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListAdminBinding
    private val adminViewModel: AdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        observeViewModel()
        syncToFirebase()

    }

    private fun setupRecyclerView() {
        val adapter = AdminAdapter(
            onDeleteClick = { admin -> adminViewModel.delete(admin) },
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun observeViewModel() {
        adminViewModel.allAdmins.observe(this) { adminList ->
            adminList?.let { (binding.recyclerView.adapter as AdminAdapter).setAdmins(it) }
        }
    }

    private fun syncToFirebase() {
        val firebaseRef = FirebaseDatabase.getInstance().getReference("admins")
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val adminList = mutableListOf<Admin>()
                for (dataSnapshot in snapshot.children) {
                    val admin = dataSnapshot.getValue(Admin::class.java)
                    if (admin != null) {
                        adminList.add(admin)
                    }
                }
                adminViewModel.syncLocalDatabase(adminList)
                runOnUiThread {
                    (binding.recyclerView.adapter as AdminAdapter).setAdmins(adminList)
                }
            }


            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
