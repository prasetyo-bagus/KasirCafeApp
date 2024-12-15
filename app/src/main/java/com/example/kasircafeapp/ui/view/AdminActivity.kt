package com.example.kasircafeapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.databinding.ActivityAdminBinding
import com.example.kasircafeapp.ui.adapter.AdminAdapter
import com.example.kasircafeapp.ui.view.fragment.RegisterFragment
import com.example.kasircafeapp.ui.viewmodel.AdminViewModel

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private val adminViewModel: AdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val email = binding.etEmail.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                val admin = Admin(username_admin = username, password_admin = password, email_admin = email)
                adminViewModel.insert(admin)

                // Clear input fields
                binding.etUsername.text.clear()
                binding.etPassword.text.clear()
                binding.etEmail.text.clear()
            }
        }

        binding.btnListAdmin.setOnClickListener{
            val intent = Intent(this, ListAdminActivity::class.java)
            startActivity(intent)
        }

    }
}
