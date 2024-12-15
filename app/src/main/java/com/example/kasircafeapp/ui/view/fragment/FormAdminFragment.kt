package com.example.kasircafeapp.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.databinding.FragmentFormAdminBinding
import com.example.kasircafeapp.databinding.FragmentRegisterBinding
import com.example.kasircafeapp.ui.viewmodel.AdminViewModel

class FormAdminFragment : Fragment() {
    private var _binding: FragmentFormAdminBinding? = null
    private val binding get() = _binding!!
    private val adminViewModel: AdminViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val adminId = it.getString("admin_id")
            val username = it.getString("username")
            val password = it.getString("password")
            val email = it.getString("email")

            binding.etUsername.setText(username)
            binding.etPassword.setText(password)
            binding.etEmail.setText(email)
        }

        binding.btnSave.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val email = binding.etEmail.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                val admin = Admin(
                    id_admin = arguments?.getString("admin_id") ?: "",
                    username_admin = username,
                    password_admin = password,
                    email_admin = email
                )
                adminViewModel.update(admin)
                Toast.makeText(requireContext(), "Admin updated successfully", Toast.LENGTH_SHORT)
                    .show()
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}