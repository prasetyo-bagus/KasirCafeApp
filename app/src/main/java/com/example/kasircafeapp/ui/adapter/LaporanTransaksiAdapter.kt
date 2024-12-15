package com.example.kasircafeapp.ui.adapter

import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Transaksi
import com.example.kasircafeapp.databinding.ItemLaporanTransaksiBinding

class LaporanTransaksiAdapter (private var transaksiList: List<Transaksi>) : RecyclerView.Adapter<LaporanTransaksiAdapter.LaporanTransaksiViewHolder>() {

    class LaporanTransaksiViewHolder(private val binding: ItemLaporanTransaksiBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(transaksi: Transaksi) {
            binding.tvTanggalLaporan.text = transaksi.tanggal
            binding.tvNamaPesananLaporan.text = transaksi.namaPesanan.joinToString("\n")
            binding.tvJumlahPesananLaporan.text = "Pesanan: ${transaksi.jumlahPesanan}"
            binding.tvTotalHargaLaporan.text = "Total: Rp. ${transaksi.totalHarga}"
            binding.tvJumlahBayarLaporan.text = "Bayar: Rp. ${transaksi.jumlahBayar}"
            binding.tvNominalKembalianLaporan.text = "Kembalian: Rp. ${transaksi.nominalKembalian}"

            binding.statusLaporan.text = if (transaksi.synchronize) {
                "Status: Berhasil"
            } else {
                "Status: Gagal Sinkronisasi"
            }

            // warna status laporan
            binding.statusLaporan.setBackgroundColor(
                if (transaksi.synchronize)
                    ContextCompat.getColor(binding.root.context, R.color.gojek_green)
                else
                    ContextCompat.getColor(binding.root.context, R.color.gofood_red)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaporanTransaksiAdapter.LaporanTransaksiViewHolder {
        val binding = ItemLaporanTransaksiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LaporanTransaksiViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LaporanTransaksiAdapter.LaporanTransaksiViewHolder, position: Int) {
        holder.bind(transaksiList[position])
    }

    override fun getItemCount() = transaksiList.size

    fun updateData(newTransaksiList: List<Transaksi>) {
        transaksiList = newTransaksiList
        notifyDataSetChanged()
    }
}