package com.example.kasircafeapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kasircafeapp.R
import com.example.kasircafeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonTransaksi.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        binding.buttonMakanan.setOnClickListener{
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        binding.buttonMinuman.setOnClickListener{
            val intent = Intent(this, FourthActivity::class.java)
            startActivity(intent)
        }

        binding.buttonAdmin.setOnClickListener{
            val intent = Intent(this, FifthActivity::class.java)
            startActivity(intent)
        }

    }
}