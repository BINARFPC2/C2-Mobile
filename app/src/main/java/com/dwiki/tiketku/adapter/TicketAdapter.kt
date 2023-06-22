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
            val timeTakeoff = dataItemTicket.dateTakeoff
            val timeLanding = dataItemTicket.dateLanding

            val takeOffSplit = timeTakeoff.split(":")
            val landingSplit = timeLanding.split(":")

            val takeOffHour = takeOffSplit[0].toInt()
            val takeOffMinute = takeOffSplit[1].toInt()

            val landingHour = landingSplit[0].toInt()
            val landingMinute = landingSplit[1].toInt()

            var hourDiff = landingHour - takeOffHour
            var minuteDiff = landingMinute - takeOffMinute

            if (minuteDiff < 0) {
                hourDiff -= 1
                minuteDiff += 60
            }

            if (hourDiff < 0) {
                hourDiff += 24
            }

            val conversionResult = String.format("%02d:%02d",hourDiff,minuteDiff)


            binding.tvDurasi.text = "${hourDiff}h ${minuteDiff}m"
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