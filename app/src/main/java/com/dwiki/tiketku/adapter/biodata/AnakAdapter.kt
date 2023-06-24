package com.dwiki.tiketku.adapter.biodata

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.databinding.ItemJenisPenumpanfBinding
import com.dwiki.tiketku.model.penumpang.PenumpangAnak

class AnakAdapter(private val listAnak:List<PenumpangAnak>):RecyclerView.Adapter<AnakAdapter.ViewHolder>() {

    private var listener:OnItemClickListener? = null

    fun setOnItemClickListener(listener:OnItemClickListener){
        this.listener = listener
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int,anak: PenumpangAnak)
    }

    inner class ViewHolder(private val binding:ItemJenisPenumpanfBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(anak: PenumpangAnak){
            binding.tvJenisPenumpang.text = anak.penumpang
            binding.cvItemJenisPenumpang.setOnClickListener {
                listener?.onItemClick(adapterPosition,anak)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnakAdapter.ViewHolder {
        val binding = ItemJenisPenumpanfBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AnakAdapter.ViewHolder, position: Int) {
       holder.bind(listAnak[position])
    }

    override fun getItemCount(): Int  = listAnak.size
}