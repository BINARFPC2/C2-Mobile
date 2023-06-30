package com.dwiki.tiketku.adapter.biodata

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.adapter.NotifikasiAdapter
import com.dwiki.tiketku.databinding.ItemCardBiodataBinding
import com.dwiki.tiketku.model.penumpang.PenumpangData

class PenumpangAdapter(private val listPenumpang:List<PenumpangData>):RecyclerView.Adapter<PenumpangAdapter.ViewHolder>() {

    class ViewHolder(private val binding:ItemCardBiodataBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(penumpangData: PenumpangData){
            binding.apply {
                tvName.text = penumpangData.name
                tvTitle.text = penumpangData.title
                tvKtp.text = penumpangData.ktppaspor
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PenumpangAdapter.ViewHolder {
        val binding = ItemCardBiodataBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PenumpangAdapter.ViewHolder, position: Int) {
        holder.bind(listPenumpang[position])
    }

    override fun getItemCount(): Int = listPenumpang.size
}