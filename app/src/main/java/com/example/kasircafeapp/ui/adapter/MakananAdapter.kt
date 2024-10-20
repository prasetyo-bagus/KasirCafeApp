package com.example.kasircafeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.databinding.ItemMakananBinding

class MakananAdapter(
    private val onDeleteClick: (Makanan) -> Unit,
    private val onEditClick: (Makanan) -> Unit,
    private val onDetailClick: (Makanan) -> Unit
) : RecyclerView.Adapter<MakananAdapter.MakananViewHolder>() {

    private var makananList = listOf<Makanan>()

    inner class MakananViewHolder(private val binding: ItemMakananBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(makanan: Makanan) {
            binding.makanan = makanan
            binding.executePendingBindings()

            binding.buttonDelete.setOnClickListener {
                onDeleteClick(makanan)
            }

            binding.buttonEdit.setOnClickListener {
                onEditClick(makanan)
            }

            binding.buttonDetail.setOnClickListener {  // Tambah event klik untuk detail
                onDetailClick(makanan)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MakananViewHolder {
        val binding = ItemMakananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MakananViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MakananViewHolder, position: Int) {
        val makanan = makananList[position]
        holder.bind(makanan)
    }

    override fun getItemCount() = makananList.size

    fun setMakanan(makanan: List<Makanan>) {
        makananList = makanan
        notifyDataSetChanged()
    }
}