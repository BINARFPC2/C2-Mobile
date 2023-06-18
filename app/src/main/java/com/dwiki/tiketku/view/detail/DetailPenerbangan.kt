package com.dwiki.tiketku.view.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentDetailPenerbanganBinding
import com.dwiki.tiketku.model.ticket.DataItemTicket
import com.dwiki.tiketku.model.ticket.DetailTicket
import com.dwiki.tiketku.util.Status
import com.dwiki.tiketku.util.Utill
import com.dwiki.tiketku.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPenerbangan : Fragment() {

    private lateinit var binding:FragmentDetailPenerbanganBinding
    private val detailViewModel:DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailPenerbanganBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getString("id")
        detailViewModel.detailTicket(id!!)
        detailViewModel.getDetailTicket.observe(viewLifecycleOwner) { detailTicket ->

            val getDetail = detailTicket.data
            if (getDetail != null){
                binding.layoutCvDetail.visibility = View.VISIBLE
                binding.layoutTotal.visibility = View.VISIBLE
                Log.d("DetailPenerbangan","Berhasil get data")
                binding.apply {
                    tvAirplaneCode.visibility = View.GONE
                    txtBandaraAwal.text = getDetail!!.airportFrom
                    txtBandaraTujuan.text = getDetail.airportTo
                    txtKeberangkatan.text = getDetail.cityFrom
                    txtTujuan.text = getDetail.cityTo
                    txtJamBerangkat.text = getDetail.dateTakeoff
                    txtTanggalBerangkat.text = getDetail.dateDeparture
                    tvPesawat.text = getDetail.airlines
                    tvInformasi.text = getDetail.information
                    txtJamDatang.text = getDetail.dateLanding
                    txtTanggalSampai.text = getDetail.dateReturn
                    val price = Utill.getPriceIdFormat(getDetail.price!!)
                    txtHargaTotal.text = "$price/pax"
                }
            } else {
                Log.e("DetailPenerbangan", "detailTicket is null")
            }

        }
    }
}