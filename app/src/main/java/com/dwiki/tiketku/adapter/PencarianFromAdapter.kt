package com.dwiki.tiketku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.databinding.ItemPencarianKotaBinding
import com.dwiki.tiketku.model.ticket.DataItemTicket
import com.dwiki.tiketku.model.ticket.HasilKota

class PencarianFromAdapter(private val listKota: List<HasilKota>):RecyclerView.Adapter<PencarianFromAdapter.ViewHolder>() {

    class ViewHolder(private val binding:ItemPencarianKotaBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(hasilKota: HasilKota){
            binding.tvKota.text = hasilKota.nama
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PencarianFromAdapter.ViewHolder {
        val binding = ItemPencarianKotaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PencarianFromAdapter.ViewHolder, position: Int) {
        holder.bind(listKota[position])
    }

    override fun getItemCount(): Int  =  listKota.size
}