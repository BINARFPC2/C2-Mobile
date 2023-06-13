package com.dwiki.tiketku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dwiki.tiketku.databinding.ItemDestinasiFavoritBinding
import com.dwiki.tiketku.model.destinasifavorit.DataItem
import com.dwiki.tiketku.util.Utill

class DestinasiFavoritAdapter(private val listDestinasiF:List<DataItem>):RecyclerView.Adapter<DestinasiFavoritAdapter.ViewHolder>() {

    class ViewHolder (private val binding:ItemDestinasiFavoritBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(favDestinasi:DataItem){
            Glide.with(itemView).load(favDestinasi.imageDestinasi).into(binding.ivDestinationFavorit)
            binding.apply {
                tvDeparture.text = favDestinasi.cityFrom
                tvDestination.text = favDestinasi.cityTo
                val price = Utill.getPriceIdFormat(favDestinasi.price)
                tvHargaDestination.text = price
                tvMaskapai.text = favDestinasi.airlines
                tvInfo.text = favDestinasi.info
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DestinasiFavoritAdapter.ViewHolder {
       val binding = ItemDestinasiFavoritBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DestinasiFavoritAdapter.ViewHolder, position: Int) {
        holder.bind(listDestinasiF[position])
    }

    override fun getItemCount(): Int  = listDestinasiF.size
}