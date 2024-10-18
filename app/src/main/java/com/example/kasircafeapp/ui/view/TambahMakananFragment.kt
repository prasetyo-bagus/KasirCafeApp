package com.example.kasircafeapp.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.databinding.FragmentTambahMakananBinding
import com.example.kasircafeapp.ui.viewmodel.MakananViewModel

class TambahMakananFragment : Fragment() {

    private var _binding: FragmentTambahMakananBinding? = null
    private val binding get() = _binding!!
    private lateinit var makananViewModel: MakananViewModel
    private var makananToEdit: Makanan? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTambahMakananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mendapatkan ViewModel dari activity
        makananViewModel = (activity as MakananActivity).makananViewModel

        // Jika mode edit, isi form dengan data makanan
        makananToEdit = arguments?.getParcelable("makanan")
        makananToEdit?.let {
            binding.editTextNama.setText(it.nama)
            binding.editTextHarga.setText(it.harga.toString())
            binding.editTextDeskripsi.setText(it.deskripsi)
            binding.editTextKategori.setText(it.kategori)
        }

        binding.buttonSimpan.setOnClickListener {
            val makanan = Makanan(
                id_makanan = makananToEdit?.id_makanan ?: 0,  // Jika mode edit, gunakan id yang sama
                nama = binding.editTextNama.text.toString(),
                harga = binding.editTextHarga.text.toString().toDouble(),
                deskripsi = binding.editTextDeskripsi.text.toString(),
                kategori = binding.editTextKategori.text.toString()
            )
            if (makananToEdit == null) {
                makananViewModel.insert(makanan)
            } else {
                makananViewModel.update(makanan)
            }
            (activity as MakananActivity).hideTambahMakananFragment()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(makanan: Makanan?): TambahMakananFragment {
            val fragment = TambahMakananFragment()
            val args = Bundle()
            args.putParcelable("makanan", makanan)
            fragment.arguments = args
            return fragment
        }
    }
}
