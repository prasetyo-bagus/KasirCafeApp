package com.example.kasircafeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.databinding.ItemAdminBinding

class AdminAdapter(
    private val adminList: List<Admin>,
    private val onEditClick: (Admin) -> Unit,
    private val onDeleteClick: (Admin) -> Unit
) : RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {

    inner class AdminViewHolder(private val binding: ItemAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(admin: Admin) {
            binding.admin = admin
            binding.executePendingBindings()
            binding.btnEdit.setOnClickListener {
                onEditClick(admin)
            }
            binding.btnDelete.setOnClickListener {
                onDeleteClick(admin)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val binding =
            ItemAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdminViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        val admin = adminList[position]
        holder.bind(admin)
    }

    override fun getItemCount(): Int = adminList.size
}
