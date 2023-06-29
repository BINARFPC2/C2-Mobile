package com.dwiki.tiketku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.ItemSetKelasBinding
import com.dwiki.tiketku.model.DummyKelas

class SetKelasAdapter(
    private val listKelas:List<DummyKelas>,
    private val listener:OnItemClickListener
    ):RecyclerView.Adapter<SetKelasAdapter.ViewHolder>() {

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView), View.OnClickListener {

        internal var className:TextView
//        internal var priceClass:TextView
        internal var layoutSetKelas:ConstraintLayout


        init {
            className = itemView.findViewById<TextView>(R.id.tv_class)
//             priceClass = itemView.findViewById<TextView>(R.id.tv_price)
            layoutSetKelas = itemView.findViewById(R.id.layout_set_kelas)
            layoutSetKelas.setOnClickListener(this)
        }

        internal fun bind(position: Int){
            className.text = listKelas[position].kelas
//            priceClass.text = listKelas[position].harga
        }

        override fun onClick(p0: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position, this@SetKelasAdapter, itemView)
            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetKelasAdapter.ViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_set_kelas,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SetKelasAdapter.ViewHolder, position: Int) {


        if (listKelas[position].isSelected){
            holder.itemView.findViewById<View>(R.id.succes_klik).visibility = View.VISIBLE
            holder.itemView.findViewById<ConstraintLayout>(R.id.layout_set_kelas).setBackgroundResource(R.drawable.curved_bg_set_kelas)
            holder.itemView.findViewById<TextView>(R.id.tv_class).setTextColor(holder.itemView.resources.getColor(R.color.white))
//            holder.itemView.findViewById<TextView>(R.id.tv_price).setTextColor(holder.itemView.resources.getColor(R.color.white))
        } else {
            holder.itemView.findViewById<View>(R.id.succes_klik).visibility = View.GONE
            holder.itemView.findViewById<ConstraintLayout>(R.id.layout_set_kelas).setBackgroundResource(R.drawable.curve_set_kelas_stroke)
        }

        (holder as ViewHolder).bind(position)

    }

    override fun getItemCount(): Int = listKelas.size



    interface OnItemClickListener{
        fun onItemClick(position: Int,adapter:SetKelasAdapter,v:View)
    }


}