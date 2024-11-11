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

class MinumanAdapter(private val listener: OnItemClickListener) : ListAdapter<Minuman, MinumanAdapter.MinumanViewHolder>(MinumanCallbak()) {

    private var selectedMinuman: Minuman? = null


    interface OnItemClickListener {
        fun onItemClick(minuman: Minuman)
    }

    inner class MinumanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNamaMinuman: TextView = itemView.findViewById(R.id.outputnamaminuman)
        private val tvHargaMinuman: TextView = itemView.findViewById(R.id.outputhargaminuman)
        private val tvKategoriMinuman: TextView = itemView.findViewById(R.id.outputkategoriminuman)

        fun bind(minuman : Minuman) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinumanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.style_minuman, parent, false)
        return MinumanViewHolder(view)
    }

    override fun onBindViewHolder(holder: MinumanViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = currentList.size

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
