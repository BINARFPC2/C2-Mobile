package com.dwiki.tiketku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.ItemSetKelasBinding
import com.dwiki.tiketku.model.DummyKelas

class SetKelasAdapter(private val listKelas:List<DummyKelas>,val selectedCard:Int):RecyclerView.Adapter<SetKelasAdapter.ViewHolder>() {

    var onItemClick: ((DummyKelas) -> Unit)? = null

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }



    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val className = itemView.findViewById<TextView>(R.id.tv_class)
        val priceClass = itemView.findViewById<TextView>(R.id.tv_price)
        val layoutSetKelas = itemView.findViewById<View>(R.id.layout_set_kelas)
        val succesIcon = itemView.findViewById<View>(R.id.succes_klik)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetKelasAdapter.ViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_set_kelas,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SetKelasAdapter.ViewHolder, position: Int) {
        val currentItem = listKelas[position]
        holder.className.text = currentItem.kelas
        holder.priceClass.text = currentItem.harga

        if (position == selectedCard){
            holder.layoutSetKelas.setBackgroundResource(R.drawable.curved_bg_set_kelas)
            holder.succesIcon.visibility = View.VISIBLE
        } else{
            holder.layoutSetKelas.setBackgroundResource(R.drawable.curve_set_kelas_stroke)
            holder.succesIcon.visibility = View.GONE
            holder.className.setTextColor(holder.itemView.resources.getColor(R.color.black))
            holder.priceClass.setTextColor(holder.itemView.resources.getColor(R.color.black))
        }

        holder.layoutSetKelas.setOnClickListener {
            onItemClickCallback?.onItemClicked(holder.adapterPosition,listKelas[holder.adapterPosition])
        }

    }

    override fun getItemCount(): Int = listKelas.size


    interface OnItemClickCallback{
        fun onItemClicked(position: Int ,data: DummyKelas)
    }
}