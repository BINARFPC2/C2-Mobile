package com.dwiki.tiketku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.databinding.ItemPenumpangRiwayatBinding
import com.dwiki.tiketku.model.riwayat.PassengersItem

class RiwayatPenumpangAdapter(private val listPenumpang:List<PassengersItem>):RecyclerView.Adapter<RiwayatPenumpangAdapter.ViewHolder>() {


    class ViewHolder(private val binding:ItemPenumpangRiwayatBinding):RecyclerView.ViewHolder(binding.root) {
        fun  bind(passengersItem: PassengersItem){
            binding.apply {
                val urutanPenumpang = adapterPosition + 1
                tvPenumpang.text = "Penumpang $urutanPenumpang : ${passengersItem.title}. ${passengersItem.name}"
                tvIdPenumpang.text = "ID:${passengersItem.id} "
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RiwayatPenumpangAdapter.ViewHolder {
        val binding = ItemPenumpangRiwayatBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RiwayatPenumpangAdapter.ViewHolder, position: Int) {
        holder.bind(listPenumpang[position])
    }

    override fun getItemCount(): Int = listPenumpang.size
}