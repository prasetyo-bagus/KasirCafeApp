package com.example.kasircafeapp.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.kasircafeapp.R
import com.example.kasircafeapp.databinding.ActivityMainBinding
import com.example.kasircafeapp.ui.view.fragment.LaporanTransaksiFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonMenu.setOnClickListener{
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

        binding.buttonLaporanTransaksi.setOnClickListener {

            val fragment = LaporanTransaksiFragment()

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_laporan_transaksi, fragment)
                .addToBackStack(null)
                .commit()

            binding.apply {
                cvDashboard.visibility = View.GONE
                linearLayoutDashboard.visibility = View.GONE
                linearLayoutDashboard2.visibility = View.GONE
                linearLayoutDashboard3.visibility = View.GONE
                fragmentContainerLaporanTransaksi.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            // Hapus fragment dari Back Stack
            supportFragmentManager.popBackStack()

            binding.apply {
                cvDashboard.visibility = View.VISIBLE
                linearLayoutDashboard.visibility = View.VISIBLE
                linearLayoutDashboard2.visibility = View.VISIBLE
                linearLayoutDashboard3.visibility = View.VISIBLE
                fragmentContainerLaporanTransaksi.visibility = View.GONE
            }
        } else {
            super.onBackPressed()
        }
    }
}