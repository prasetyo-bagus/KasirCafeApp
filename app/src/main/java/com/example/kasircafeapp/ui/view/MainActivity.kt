package com.example.kasircafeapp.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.kasircafeapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttomMenu.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        binding.buttonMakanan.setOnClickListener{
            val intent = Intent(this, MakananActivity::class.java)
            startActivity(intent)
        }

        binding.buttonMinuman.setOnClickListener{
            val intent = Intent(this, MinumanActivity::class.java)
            startActivity(intent)
        }

        binding.buttonAdmin.setOnClickListener{
            val intent = Intent(this, AdminActivity::class.java)
            startActivity(intent)
        }

    }
}