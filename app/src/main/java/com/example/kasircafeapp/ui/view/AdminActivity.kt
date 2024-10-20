package com.example.kasircafeapp.ui.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.databinding.ActivityAdminBinding
import com.example.kasircafeapp.ui.adapter.AdminAdapter
import com.example.kasircafeapp.ui.viewmodel.AdminViewModel

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private val adminViewModel: AdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // RecyclerView setup
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Mengamati LiveData untuk perubahan
        adminViewModel.getAllAdmins().observe(this, Observer { adminList ->
            if (adminList != null) {
                binding.recyclerView.adapter = AdminAdapter(adminList, { admin ->
                    showEditAdminFragment(admin)
                }, { admin ->
                    adminViewModel.deleteAdmin(admin)
                })
            }
        })

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val email = binding.etEmail.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                val admin = Admin(username = username, password = password, email = email)
                adminViewModel.registerAdmin(admin)
            }
        }
    }

    private fun showEditAdminFragment(admin: Admin) {
        val fragment = FormAdminFragment()
        val bundle = Bundle().apply {
            putInt("admin_id", admin.id_admin)
            putString("username", admin.username)
            putString("password", admin.password)
            putString("email", admin.email)
        }
        fragment.arguments = bundle

        supportFragmentManager.commit {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
        }

        // Sembunyikan daftar admin ketika form terbuka
        binding.batas.visibility = View.GONE
    }

    fun hideEditAdminFragment() {
        // Tampilkan kembali daftar admin ketika form ditutup
        binding.batas.visibility = View.VISIBLE
        supportFragmentManager.popBackStack()
    }
}
