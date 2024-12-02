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
import com.example.kasircafeapp.databinding.ItemMenuMakananBinding
import com.example.kasircafeapp.databinding.ItemMenuMinumanBinding
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.min

// Adapter untuk daftar minuman
class MinumanAdapter(private val listener: OnItemClickListener) : ListAdapter<Minuman, RecyclerView.ViewHolder>(MinumanDiffCallback()) {

    private var selectedMinuman: Minuman? = null
    var useLayoutMenuMinuman: Boolean = false

    interface OnItemClickListener {
        fun onItemClick(minuman: Minuman)
    }

    inner class MinumanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNamaMinuman: TextView = itemView.findViewById(R.id.outputnamaminuman)
        private val tvHargaMinuman: TextView = itemView.findViewById(R.id.outputhargaminuman)
        private val tvKategoriMinuman: TextView = itemView.findViewById(R.id.outputkategoriminuman)

        fun bind(minuman: Minuman) {
            tvNamaMinuman.text = minuman.nama_minuman
            tvHargaMinuman.text = minuman.harga_minuman.toString()
            tvKategoriMinuman.text = minuman.kategori_minuman

            itemView.setOnClickListener {
                selectedMinuman = minuman
                listener.onItemClick(minuman)
                notifyDataSetChanged()
            }
        }
    }

    // ViewHolder untuk layout menu Minuman
    inner class MenuMinumanViewHolder(private val binding: ItemMenuMinumanBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(minuman: Minuman) {
                binding.ivMenuMinuman.setImageResource(R.drawable.minuman_cover)
                binding.tvMenuNamaMinuman.text = minuman.nama_minuman
//                binding.tvMenuHargaMinuman.text = String.format("Rp. %.2f", minuman.harga_minuman)
                val numberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
                numberFormat.maximumFractionDigits = 2
                binding.tvMenuHargaMinuman.text = numberFormat.format(minuman.harga_minuman)
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

//    override fun onBindViewHolder(holder: MinumanViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }

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


class NumberListAdapter : ListAdapter<Int, NumberListAdapter.IntViewHolder>(RowItemDiffCallback()) {

    class IntViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val textView: TextView = row.findViewById(R.id.number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.style_minuman, parent, false)
        return IntViewHolder(view)
    }

    override fun onBindViewHolder(holder: IntViewHolder, position: Int) {
        holder.textView.text = getItem(position).toString()
    }

    class RowItemDiffCallback : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}