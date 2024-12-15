package com.example.kasircafeapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kasircafeapp.data.entity.Admin
import com.example.kasircafeapp.databinding.ItemAdminBinding

class AdminAdapter(
    private val onDeleteClick: (Admin) -> Unit = {},
) : RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {

    private var adminList = listOf<Admin>()

    inner class AdminViewHolder(private val binding: ItemAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(admin: Admin) {
            binding.admin = admin
            binding.tvUsername.text = admin.username_admin
            binding.btnDelete.setOnClickListener { onDeleteClick(admin) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val binding = ItemAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AdminViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        holder.bind(adminList[position])
    }

    override fun getItemCount(): Int = adminList.size

    fun setAdmins(admins: List<Admin>) {
        adminList = admins
        notifyDataSetChanged()
    }
}