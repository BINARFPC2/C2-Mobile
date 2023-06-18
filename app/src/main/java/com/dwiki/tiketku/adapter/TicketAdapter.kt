package com.dwiki.tiketku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.databinding.ItemHasilPencarianBinding
import com.dwiki.tiketku.model.ticket.DataItemTicket
import com.dwiki.tiketku.util.Utill

class TicketAdapter(
    private val listTikets:List<DataItemTicket>,
    private val onSelect:(DataItemTicket) -> Unit

    ):RecyclerView.Adapter<TicketAdapter.ViewHolder>() {

    class ViewHolder (private val binding:ItemHasilPencarianBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(dataItemTicket: DataItemTicket,onSelect: (DataItemTicket) -> Unit){
            binding.tvJamKeberangkatan.text = dataItemTicket.dateTakeoff
            binding.tvJamSampai.text = dataItemTicket.dateLanding
            binding.tvKotaKeberangkatan.text = dataItemTicket.cityFrom
            binding.tvKotaSampai.text = dataItemTicket.cityTo
            val price = Utill.getPriceIdFormat(dataItemTicket.price)
            binding.tvHarga.text = price
            binding.tvPesawat.text = dataItemTicket.airlines
            binding.cvHasilPencarian.setOnClickListener {
                onSelect(dataItemTicket)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketAdapter.ViewHolder {
        val binding = ItemHasilPencarianBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketAdapter.ViewHolder, position: Int) {
     holder.bind(listTikets[position],onSelect)
    }

    override fun getItemCount(): Int  = listTikets.size
}