package com.example.kasircafeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.databinding.ItemMakananBinding
import com.example.kasircafeapp.databinding.ItemMenuMakananBinding
import java.text.NumberFormat
import java.util.Locale

class MakananAdapter(
    private val onDeleteClick: (Makanan) -> Unit = {},
    private val onEditClick: (Makanan) -> Unit = {},
    private val onDetailClick: (Makanan) -> Unit = {},
    private val useLayoutMenuMakanan: Boolean = false
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var makananList = listOf<Makanan>()

    // ViewHolder untuk ItemMakanan
    inner class MakananViewHolder(private val binding: ItemMakananBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(makanan: Makanan) {
            binding.makanan = makanan
            binding.executePendingBindings()

            binding.buttonDelete.setOnClickListener { onDeleteClick(makanan) }
            binding.buttonEdit.setOnClickListener { onEditClick(makanan) }
            binding.buttonDetail.setOnClickListener { onDetailClick(makanan) }
        }
    }

    // ViewHolder untuk ItemMenuMakanan
    inner class MenuMakananViewHolder(private val binding: ItemMenuMakananBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(makanan: Makanan) {
            binding.ivMenuMakanan.setImageResource(R.drawable.food_cover)
            binding.tvMenuNamaMakanan.text = makanan.nama
//            binding.tvMenuHargaMakanan.text = String.format("Rp. %.2f", makanan.harga)
            val numberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            numberFormat.maximumFractionDigits = 2
            binding.tvMenuHargaMakanan.text = numberFormat.format(makanan.harga)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (useLayoutMenuMakanan) {
            val binding = ItemMenuMakananBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MenuMakananViewHolder(binding)
        } else {
            val binding = ItemMakananBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MakananViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val makanan = makananList[position]
        when (holder) {
            is MakananViewHolder -> holder.bind(makanan)
            is MenuMakananViewHolder -> holder.bind(makanan)
        }
    }

    override fun getItemCount(): Int = makananList.size

    fun setMakanan(makanan: List<Makanan>) {
        makananList = makanan
        notifyDataSetChanged()
    }
}
