package com.example.kasircafeapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.databinding.FragmentFormAdminBinding
import com.example.kasircafeapp.ui.viewmodel.AdminViewModel

class FormAdminFragment : Fragment() {
    private var _binding: FragmentFormAdminBinding? = null
    private val binding get() = _binding!!
    private val adminViewModel: AdminViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ambil data admin dari bundle
        val adminId = arguments?.getInt("admin_id") ?: -1
        val username = arguments?.getString("username") ?: ""
        val password = arguments?.getString("password") ?: ""
        val email = arguments?.getString("email") ?: ""

        // Set nilai awal pada form
        binding.etUsername.setText(username)
        binding.etPassword.setText(password)
        binding.etEmail.setText(email)

        // Update Admin
        binding.btnSave.setOnClickListener {
            val updatedUsername = binding.etUsername.text.toString()
            val updatedPassword = binding.etPassword.text.toString()
            val updatedEmail = binding.etEmail.text.toString()

            if (adminId != -1) {
                val updatedAdmin = Admin(adminId, updatedUsername, updatedPassword, updatedEmail)
                adminViewModel.updateAdmin(updatedAdmin)
                Toast.makeText(requireContext(), "Admin updated", Toast.LENGTH_SHORT).show()

                (activity as AdminActivity).hideEditAdminFragment()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
