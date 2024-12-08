package com.example.kasircafeapp.ui.view.fragment

import android.os.Bundle
import android.util.Log
import android.util.Log.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Transaksi
import com.example.kasircafeapp.databinding.FragmentDetilTransaksiBinding
import com.example.kasircafeapp.ui.viewmodel.MenuViewModel

class DetilTransaksiFragment : Fragment() {

    private var _binding: FragmentDetilTransaksiBinding? = null
    private val binding get() = _binding!!
    private lateinit var menuViewModel: MenuViewModel
    private var transaksi: Transaksi? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetilTransaksiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuViewModel = ViewModelProvider(requireActivity()).get(MenuViewModel::class.java)

        // Menerima data transaksi dari Bundle
        arguments?.let {
            transaksi = it.getParcelable("transaksi")
                ?: throw IllegalArgumentException("Data Transaksi Tidak Ditemukan")
        }

        transaksi?.let { transaksiData ->

            val namaPesanan = transaksiData.namaPesanan
            val pemisahNamaPesanan = namaPesanan.joinToString("\n")
            binding.tvNamaPesananTransaksi.text = pemisahNamaPesanan
            binding.tvTanggalTransaksi.text = transaksiData.tanggal
            binding.tvJumlahBayar.text = "RP. ${transaksiData.jumlahBayar.toString()}"
            binding.tvJumlahTransaksi.text = transaksiData.jumlahPesanan.toString()
            binding.tvTotalTransaksi.text = "Rp. ${transaksiData.totalHarga}"
            binding.tvNominalKembalian.text = "Rp. ${transaksiData.nominalKembalian.toString()}"

            binding.btnBayarTransaksi.setOnClickListener {
                menuViewModel.insertTransaksi(transaksiData)

                val fragment = LaporanTransaksiFragment()
                val bundle = Bundle()
                bundle.putParcelable("transaksi", transaksi)
                fragment.arguments = bundle
                Toast.makeText(requireContext(), "Pembayaran Berhasil", Toast.LENGTH_SHORT).show()

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_transaksi, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}