package com.example.kasircafeapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.databinding.ActivityAdminBinding
import com.example.kasircafeapp.ui.adapter.AdminAdapter
import com.example.kasircafeapp.ui.viewmodel.AdminViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding
    private val adminViewModel: AdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Register Admin
        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val email = binding.etEmail.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                // Register Admin to Firebase Authentication
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            val userId = FirebaseAuth.getInstance().currentUser?.uid

                            // Add admin data to Realtime Database
                            val admin = Admin(username_admin = username, password_admin = password, email_admin = email)
                            val databaseRef = FirebaseDatabase.getInstance().getReference("admins")
                            userId?.let {
                                databaseRef.child(it).setValue(admin).addOnCompleteListener { dbTask ->
                                    if (dbTask.isSuccessful) {
                                        // Optionally, save admin locally using ViewModel or Room
                                        adminViewModel.insert(admin)
                                        Toast.makeText(this, "Admin Registered Successfully", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(this, "Failed to save admin to database", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                            // Clear input fields
                            binding.etUsername.text.clear()
                            binding.etPassword.text.clear()
                            binding.etEmail.text.clear()

                        } else {
                            Toast.makeText(this, "Registration failed: ${authTask.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigate to ListAdminActivity
        binding.btnListAdmin.setOnClickListener {
            val intent = Intent(this, ListAdminActivity::class.java)
            startActivity(intent)
        }
    }
}
