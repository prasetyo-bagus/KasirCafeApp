package com.example.kasircafeapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.databinding.FragmentTambahMakananBinding
import com.example.kasircafeapp.ui.viewmodel.MakananViewModel

class TambahMakananFragment : Fragment() {

    private var _binding: FragmentTambahMakananBinding? = null
    private val binding get() = _binding!!
    private lateinit var makananViewModel: MakananViewModel
    private var makanan: Makanan? = null // Variabel untuk menyimpan makanan yang diedit

    fun setMakanan(makanan: Makanan?) {
        this.makanan = makanan // Menyimpan makanan
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTambahMakananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        makananViewModel = (activity as MakananActivity).makananViewModel

        // Mengisi data jika makanan tidak null
        makanan?.let {
            binding.editTextNama.setText(it.nama)
            binding.editTextHarga.setText(it.harga.toString())
            binding.editTextDeskripsi.setText(it.deskripsi)
            binding.editTextKategori.setText(it.kategori)
        }

        binding.buttonSimpan.setOnClickListener {
            val newMakanan = Makanan(
                id_makanan = makanan?.id_makanan ?: 0,
                nama = binding.editTextNama.text.toString(),
                harga = binding.editTextHarga.text.toString().toDouble(),
                deskripsi = binding.editTextDeskripsi.text.toString(),
                kategori = binding.editTextKategori.text.toString()
            )

            if (makanan == null) {
                makananViewModel.insert(newMakanan)
            } else {
                makananViewModel.update(newMakanan)
            }
            (activity as MakananActivity).hideTambahMakananFragment()
        }
        binding.buttonBatal.setOnClickListener {
            // Menghilangkan fragment dan kembali ke MakananActivity
            (activity as MakananActivity).hideTambahMakananFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}