package com.example.kasircafeapp.ui.view

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.databinding.FragmentTambahMakananBinding
import com.example.kasircafeapp.ui.viewmodel.MakananViewModel
import java.util.UUID

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

        val kategoriList = listOf("Makanan Utama", "Makanan Penutup", "Makanan Pendamping", "Cemilan")
        val kategoriAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            kategoriList
        )
        binding.spinnerKategori.setAdapter(kategoriAdapter)
        // Mengisi data jika makanan tidak null
        makanan?.let {
            binding.editTextNama.setText(it.nama_makanan)
            binding.editTextHarga.setText(it.harga_makanan.toString())
            binding.editTextDeskripsi.setText(it.deskripsi_makanan)
            binding.spinnerKategori.setText(it.kategori_makanan, false)
        }

        binding.buttonSimpan.setOnClickListener {
            val namaMakanan = binding.editTextNama.text.toString()
            val hargaMakanan = binding.editTextHarga.text.toString().toDoubleOrNull()
            val kategoriMakanan = binding.spinnerKategori.text.toString()

            if (namaMakanan.isEmpty() || hargaMakanan == null) {
                Toast.makeText(requireContext(), "Nama dan Harga tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Jika sedang menambahkan data baru, buat ID baru dengan UUID
            val newIdMakanan = makanan?.id_makanan ?: UUID.randomUUID().toString()
            val newMakanan = Makanan(
                id_makanan = newIdMakanan,
                nama_makanan = binding.editTextNama.text.toString(),
                harga_makanan = binding.editTextHarga.text.toString().toDouble(),
                deskripsi_makanan = binding.editTextDeskripsi.text.toString(),
                kategori_makanan = kategoriMakanan
            )

            if (makanan == null) {
                // Data baru
                makananViewModel.insert(newMakanan)
            } else {
                // Update data yang ada
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