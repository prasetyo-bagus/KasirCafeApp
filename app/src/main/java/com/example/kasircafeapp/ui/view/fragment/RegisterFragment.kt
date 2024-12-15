package com.example.kasircafeapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.databinding.FragmentRegisterBinding
import com.example.kasircafeapp.ui.viewmodel.AdminViewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val adminViewModel: AdminViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            val email = binding.etEmail.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                val admin = Admin(username_admin = username, password_admin = password, email_admin = email)
                adminViewModel.insert(admin)
                Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()

                // Clear input fields
                binding.etUsername.text.clear()
                binding.etPassword.text.clear()
                binding.etEmail.text.clear()

                // Navigate back to LoginFragment
                Navigation.findNavController(view).navigate(R.id.registerToLogin)
            } else {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvToLogin.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.registerToLogin)
        }
    }
}