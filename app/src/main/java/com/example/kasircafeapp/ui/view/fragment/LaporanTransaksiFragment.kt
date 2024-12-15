package com.example.kasircafeapp.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kasircafeapp.R
import com.example.kasircafeapp.databinding.FragmentLaporanTransaksiBinding
import com.example.kasircafeapp.ui.adapter.LaporanTransaksiAdapter
import com.example.kasircafeapp.ui.viewmodel.MenuViewModel

class LaporanTransaksiFragment : Fragment() {

    private var _binding: FragmentLaporanTransaksiBinding? = null
    private val binding get() = _binding
    private lateinit var adapter: LaporanTransaksiAdapter
    private lateinit var menuViewModel: MenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLaporanTransaksiBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuViewModel = ViewModelProvider(requireActivity()).get(MenuViewModel::class.java)
        adapter = LaporanTransaksiAdapter(emptyList())
        binding?.recyclerViewLaporan?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recyclerViewLaporan?.adapter = adapter

        observeLocalData()
    }

    private fun observeLocalData() {
        menuViewModel.allTransaksi.observe(viewLifecycleOwner, Observer { transaksiList ->
            if (transaksiList.isNotEmpty()) {
                adapter.updateData(transaksiList)
            } else {
                Toast.makeText(requireContext(), "Tidak ada data transaksi", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
