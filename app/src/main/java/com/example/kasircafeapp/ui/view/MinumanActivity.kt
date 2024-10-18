package com.example.kasircafeapp.ui.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Minuman
import com.example.kasircafeapp.databinding.ActivityMinumanBinding
import com.example.kasircafeapp.ui.viewmodel.MinumanViewModel
import androidx.lifecycle.Observer

class MinumanActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMinumanBinding
    private val minumanViewModel: MinumanViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMinumanBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        minumanViewModel.allMinuman.observe(this, {minuman ->
//            binding.outputnamaminuman
//        })
    }
}