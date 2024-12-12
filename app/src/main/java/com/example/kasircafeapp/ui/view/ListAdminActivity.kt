package com.example.kasircafeapp.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.databinding.ActivityListAdminBinding
import com.example.kasircafeapp.ui.adapter.AdminAdapter
import com.example.kasircafeapp.ui.viewmodel.AdminViewModel
import com.example.kasircafeapp.ui.view.fragment.FormAdminFragment

class ListAdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListAdminBinding
    private val adminViewModel: AdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        adminViewModel.getAllAdmins().observe(this) { adminList ->
            binding.recyclerView.adapter = AdminAdapter(adminList, { admin ->
                openEditAdminFragment(admin)
            }, { admin ->
                adminViewModel.deleteAdmin(admin)
            })
        }
    }

    private fun openEditAdminFragment(admin: Admin) {
        val fragment = FormAdminFragment().apply {
            arguments = Bundle().apply {
                putInt("admin_id", admin.id_admin)
                putString("username", admin.username)
                putString("password", admin.password)
                putString("email", admin.email)
            }
        }

        // Gantikan fragment saat ini dengan FormAdminFragment
        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)  // Menambahkan fragment ke back stack
        }
    }
}
