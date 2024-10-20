package com.example.kasircafeapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kasircafeapp.R
import com.example.kasircafeapp.databinding.FragmentDetailMakananBinding
import com.example.kasircafeapp.ui.view.MakananActivity

class DetailMakananFragment : Fragment() {

    private lateinit var binding: FragmentDetailMakananBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailMakananBinding.inflate(inflater, container, false)

        val nama = arguments?.getString("nama")
        val harga = arguments?.getDouble("harga")
        val deskripsi = arguments?.getString("deskripsi")
        val kategori = arguments?.getString("kategori")

        // Tampilkan data di View
        binding.textViewNama.text = nama
        binding.textViewHarga.text = "Rp."+harga.toString()
        binding.textViewDeskripsi.text = deskripsi
        binding.textViewKategori.text = kategori

        // Tombol Back untuk kembali ke MakananActivity
        binding.buttonBack.setOnClickListener {
            // Menghilangkan fragment dan kembali ke MakananActivity
            (activity as MakananActivity).hideTambahMakananFragment()
        }

        return binding.root
    }
}