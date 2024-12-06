package com.example.kasircafeapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Minuman
import com.example.kasircafeapp.databinding.ItemMenuMinumanBinding
import java.text.NumberFormat
import java.util.Locale

// Adapter untuk daftar minuman
class MinumanAdapter(
    private val listener: OnItemClickListener,
    private val dataChangedListener: onDataChangedListener
) : ListAdapter<Minuman, RecyclerView.ViewHolder>(
    MinumanDiffCallback()
) {

    private var selectedMinuman: Minuman? = null
    var useLayoutMenuMinuman: Boolean = false
    private val pesananMap = mutableMapOf<Minuman, Pair<Int, Double>>()
    private val namaMinumanList = mutableListOf<String>()


    interface OnItemClickListener {
        fun onItemClick(minuman: Minuman)
    }

    interface onDataChangedListener {
        fun onDataChanged(totalHarga: Double, jumlahPesanan: Int, namaMinumanList: List<String>)
    }

    inner class MinumanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNamaMinuman: TextView = itemView.findViewById(R.id.outputnamaminuman)
        private val tvHargaMinuman: TextView = itemView.findViewById(R.id.outputhargaminuman)
        private val tvKategoriMinuman: TextView = itemView.findViewById(R.id.outputkategoriminuman)

        fun bind(minuman: Minuman) {
            tvNamaMinuman.text = minuman.nama_minuman
            tvHargaMinuman.text = formatCurrency(minuman.harga_minuman)
            tvKategoriMinuman.text = minuman.kategori_minuman

            itemView.setOnClickListener {
                selectedMinuman = minuman
                listener.onItemClick(minuman)
                notifyDataSetChanged()
            }
        }
    }

    fun formatCurrency(amount: Double): String {
        val locale = Locale("id", "ID")
        val formatter = NumberFormat.getCurrencyInstance(locale)
        return formatter.format(amount)
    }


    // ViewHolder untuk layout menu Minuman
    inner class MenuMinumanViewHolder(private val binding: ItemMenuMinumanBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var jumlahPesanan = 0
        private var totalHarga = 0.0

        fun bind(minuman: Minuman) {
            binding.ivMenuMinuman.setImageResource(R.drawable.minuman_cover)
            binding.tvMenuNamaMinuman.text = minuman.nama_minuman
            binding.tvMenuHargaMinuman.text = formatCurrency(minuman.harga_minuman)

            binding.tvJumlahPesananMinuman.text = "0"
            jumlahPesanan = 0
            totalHarga = minuman.harga_minuman

            binding.btnTambahMenuMinuman.setOnClickListener {
                jumlahPesanan++
                totalHarga = minuman.harga_minuman * jumlahPesanan
                pesananMap[minuman] = Pair(jumlahPesanan, totalHarga) // Update pesananMap
                binding.tvJumlahPesananMinuman.text = jumlahPesanan.toString()

                // Menambahkan nama minuman ke daftar namaMinumanList jika belum ada
                if (!namaMinumanList.contains(minuman.nama_minuman)) {
                    namaMinumanList.add(minuman.nama_minuman)
                }

                // Update total keseluruhan di listener
                val totalHargaKeseluruhan = pesananMap.values.sumByDouble { it.second }
                val jumlahPesananKeseluruhan = pesananMap.values.sumOf { it.first }
                dataChangedListener.onDataChanged(totalHargaKeseluruhan, jumlahPesananKeseluruhan, namaMinumanList)
            }

            binding.btnKurangMenuMinuman.setOnClickListener {
                if (jumlahPesanan > 0) {
                    jumlahPesanan--
                    totalHarga = minuman.harga_minuman * jumlahPesanan
                    pesananMap[minuman] = Pair(jumlahPesanan, totalHarga) // Update pesananMap
                    binding.tvJumlahPesananMinuman.text = jumlahPesanan.toString()

                    // Jika jumlah pesanan menjadi 0, hapus nama minuman dari daftar
                    if (jumlahPesanan == 0 && namaMinumanList.contains(minuman.nama_minuman)) {
                        namaMinumanList.remove(minuman.nama_minuman)
                    }

                    // Update total keseluruhan di listener
                    val totalHargaKeseluruhan = pesananMap.values.sumByDouble { it.second }
                    val jumlahPesananKeseluruhan = pesananMap.values.sumOf { it.first }
                    dataChangedListener.onDataChanged(totalHargaKeseluruhan, jumlahPesananKeseluruhan, namaMinumanList)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (useLayoutMenuMinuman) {
            val binding = ItemMenuMinumanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MenuMinumanViewHolder(binding)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.style_minuman,
                parent,
                false
            )
            MinumanViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val minuman = getItem(position)
        when (holder) {
            is MinumanViewHolder -> holder.bind(minuman)
            is MenuMinumanViewHolder -> holder.bind(minuman)
        }
    }

    fun getSelectedMinuman(): Minuman? {
        return selectedMinuman
    }

    class MinumanDiffCallback : DiffUtil.ItemCallback<Minuman>() {
        override fun areItemsTheSame(oldItem: Minuman, newItem: Minuman): Boolean {
            return oldItem.id_minuman == newItem.id_minuman
        }

        override fun areContentsTheSame(oldItem: Minuman, newItem: Minuman): Boolean {
            return oldItem == newItem
        }
    }
}