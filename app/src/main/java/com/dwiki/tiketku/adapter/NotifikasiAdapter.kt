package com.dwiki.tiketku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.databinding.ItemNotifikasiBinding
import com.dwiki.tiketku.model.notifikasi.DataItemNotifikasi

class NotifikasiAdapter(private val listNofitikasi:List<DataItemNotifikasi>):RecyclerView.Adapter<NotifikasiAdapter.ViewHolder>() {

    class ViewHolder(private val binding:ItemNotifikasiBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(notifikasi:DataItemNotifikasi){
            binding.tvNotifikasi.text = notifikasi.message
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotifikasiAdapter.ViewHolder {
        val binding = ItemNotifikasiBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotifikasiAdapter.ViewHolder, position: Int) {
        holder.bind(listNofitikasi[position])
    }

    override fun getItemCount(): Int  = listNofitikasi.size
}