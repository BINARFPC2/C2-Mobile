package com.dwiki.tiketku.adapter.biodata

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.databinding.ItemJenisPenumpanfBinding
import com.dwiki.tiketku.model.penumpang.PenumpangBayi

class BayiAdapter(private val listBayi:List<PenumpangBayi>):RecyclerView.Adapter<BayiAdapter.ViewHolder>() {

    private var listener:OnItemClickListener? = null

    fun setOnItemClickListener(listener:OnItemClickListener){
        this.listener = listener
    }


    interface OnItemClickListener{
        fun onItemClick(position: Int,bayi: PenumpangBayi)
    }

    inner class ViewHolder(private val binding:ItemJenisPenumpanfBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(bayi: PenumpangBayi){
            binding.tvJenisPenumpang.text = bayi.penumpang
            binding.cvItemJenisPenumpang.setOnClickListener {
                listener?.onItemClick(adapterPosition,bayi)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BayiAdapter.ViewHolder {
        val binding = ItemJenisPenumpanfBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BayiAdapter.ViewHolder, position: Int) {
        holder.bind(listBayi[position])
    }

    override fun getItemCount(): Int  = listBayi.size
}