package com.dwiki.tiketku.adapter.biodata

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.databinding.ItemJenisPenumpanfBinding
import com.dwiki.tiketku.model.penumpang.Penumpang


class DewasaAdapter(private val listDewasa:List<Penumpang>):RecyclerView.Adapter<DewasaAdapter.ViewHolder>() {

    private var listener:OnItemClickListener? =null

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener = listener
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    inner class ViewHolder (private val binding:ItemJenisPenumpanfBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(dewasa: Penumpang){
            binding.tvJenisPenumpang.text = dewasa.penumpang
            binding.cvItemJenisPenumpang.setOnClickListener {
               listener?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DewasaAdapter.ViewHolder {
       val binding = ItemJenisPenumpanfBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DewasaAdapter.ViewHolder, position: Int) {
        holder.bind(listDewasa[position])
    }

    override fun getItemCount(): Int  = listDewasa.size


}