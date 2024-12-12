package com.example.kasircafeapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Admin

class AdminAdapter(
    private val adminList: List<Admin>,
    private val onEditClick: (Admin) -> Unit,  // Callback untuk tombol edit
    private val onDeleteClick: (Admin) -> Unit // Callback untuk tombol delete
) : RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_admin, parent, false)
        return AdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        val admin = adminList[position] // Menyimpan objek admin yang tepat
        holder.bind(admin) // Mengirim objek admin ke holder untuk ditampilkan
    }

    override fun getItemCount() = adminList.size

    inner class AdminViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvUsername: TextView = itemView.findViewById(R.id.tvUsername)
        private val tvEmail: TextView = itemView.findViewById(R.id.tvEmail)
        private val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        private val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(admin: Admin) {
            tvUsername.text = admin.username // Menampilkan nama admin
            tvEmail.text = admin.email

            // Aksi untuk tombol edit
            btnEdit.setOnClickListener {
                onEditClick(admin)  // Memanggil fungsi onEditClick dengan objek admin
            }

            // Aksi untuk tombol delete
            btnDelete.setOnClickListener {
                onDeleteClick(admin) // Memanggil fungsi onDeleteClick dengan objek admin
            }
        }
    }
}
