package com.example.kasircafeapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kasircafeapp.R
import com.example.kasircafeapp.data.entity.Minuman

class MinumanAdapter(
    private var minumanList: List<Minuman>
) : RecyclerView.Adapter<MinumanAdapter.MinumanViewHolder>() {

    // ViewHolder untuk memegang referensi ke item view
    class MinumanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaMinuman: TextView = itemView.findViewById(R.id.outputnamaminuman)
        val hargaMinuman: TextView = itemView.findViewById(R.id.outputhargaminuman)
        val kategoriMinuman: TextView = itemView.findViewById(R.id.textView)
    }

    // Menghubungkan layout item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinumanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.style_minuman, parent, false)
        return MinumanViewHolder(view)
    }

    // Menghubungkan data dengan ViewHolder
    override fun onBindViewHolder(holder: MinumanViewHolder, position: Int) {
        val currentMinuman = minumanList[position]
        holder.namaMinuman.text = currentMinuman.nama_minuman
        holder.hargaMinuman.text = currentMinuman.harga_minuman.toString()
        holder.kategoriMinuman.text = currentMinuman.kategoti_minuman
    }

    // Menghitung jumlah item
    override fun getItemCount() = minumanList.size

    // Update data di adapter
    fun updateData(newMinumanList: List<Minuman>) {
        minumanList = newMinumanList
        notifyDataSetChanged() // Memberitahu adapter bahwa data telah berubah
    }
}
