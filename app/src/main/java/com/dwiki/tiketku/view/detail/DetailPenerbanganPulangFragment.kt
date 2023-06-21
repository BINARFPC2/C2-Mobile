package com.dwiki.tiketku.view.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentDetailPenerbanganPulangBinding
import com.dwiki.tiketku.util.Utill
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.dwiki.tiketku.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailPenerbanganPulangFragment : Fragment() {

    private lateinit var binding:FragmentDetailPenerbanganPulangBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val berandaViewModel:BerandaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailPenerbanganPulangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idReturn = berandaViewModel.getIdReturn()
        detailViewModel.detailTicket(idReturn!!)
        detailViewModel.getDetailTicket.observe(viewLifecycleOwner) { detailTicket ->

            val getDetail = detailTicket.data
            if (getDetail != null){
                binding.layoutCvDetail.visibility = View.VISIBLE
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
                    txtTanggalSampai.text = getDetail.dateEnd
                    val price = Utill.getPriceIdFormat(getDetail.price!!)
                }
            } else {
                Log.e("DetailPenerbangan", "detailTicket is null")
            }

        }
    }
}