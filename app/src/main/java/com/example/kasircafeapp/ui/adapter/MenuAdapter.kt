package com.example.kasircafeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kasircafeapp.data.relation.MenuWithDetails
import com.example.kasircafeapp.databinding.ItemMenuBinding

class MenuAdapter : ListAdapter<MenuWithDetails, MenuAdapter.MenuViewHolder>(MenuDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class MenuViewHolder(private val binding: ItemMenuBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menuWithDetails: MenuWithDetails) {
            binding.tvNamaMakanan.text = menuWithDetails.makanan?.nama
            binding.tvNamaMinuman.text = menuWithDetails.minuman?.nama_minuman
            binding.tvJumlah.text = menuWithDetails.menu.jumlah.toString()
            binding.tvTotalHarga.text = "Rp ${menuWithDetails.menu.totalHarga}"
        }
    }

}

class MenuDiffCallback : DiffUtil.ItemCallback<MenuWithDetails>() {
    override fun areItemsTheSame(oldItem: MenuWithDetails, newItem: MenuWithDetails): Boolean {
        return oldItem.menu.id_menu == newItem.menu.id_menu
    }

    override fun areContentsTheSame(oldItem: MenuWithDetails, newItem: MenuWithDetails): Boolean {
        return oldItem == newItem
    }
}
