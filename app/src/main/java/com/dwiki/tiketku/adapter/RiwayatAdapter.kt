package com.dwiki.tiketku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.databinding.ItemRiwayatPesananBinding
import com.dwiki.tiketku.model.history.DataItemRiwayat
import com.dwiki.tiketku.util.Utill

class RiwayatAdapter(private val listRiwayat:List<DataItemRiwayat>):RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {

    class ViewHolder(private val binding:ItemRiwayatPesananBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(data:DataItemRiwayat){
            binding.apply {
                keberangkatan.text = data.departureTicket!!.cityFrom
                tglKeberangkatan.text = data.departureTicket.dateDeparture
                jamKeberangkatan.text = data.departureTicket.dateTakeoff
                kedatangan.text = data.departureTicket.cityTo
                tglKedatangan.text = data.departureTicket.dateEnd
                jamKedatangan.text = data.departureTicket.dateLanding
                tvCode.text = data.departureTicket!!.bookingCode
                tvClass.text = data.departureTicket.typeSeat
                val price = data.totalPrice
                val idrPrice = Utill.getPriceIdFormat(price!!)
                tvHarga.text = idrPrice

                if (data.returnTicket != null){
                    binding.apply {
                        layoutReturn.visibility = View.VISIBLE
                        tvBookingCodeReturn.visibility = View.VISIBLE
                        tvCodeReturn.visibility = View.VISIBLE

                        keberangkatanReturn.text = data.returnTicket.cityFrom
                        tglKeberangkatanReturn.text = data.returnTicket.dateDeparture
                        jamKeberangkatanReturn.text = data.returnTicket.dateTakeoff
                        kedatanganReturn.text = data.returnTicket.cityTo
                        tglKedatanganReturn.text = data.returnTicket.dateEnd
                        jamKedatanganReturn.text = data.returnTicket.dateLanding
                        tvCodeReturn.text = data.returnTicket.bookingCode
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatAdapter.ViewHolder {
        val binding = ItemRiwayatPesananBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RiwayatAdapter.ViewHolder, position: Int) {
        holder.bind(listRiwayat[position])
    }

    override fun getItemCount(): Int  = listRiwayat.size
}