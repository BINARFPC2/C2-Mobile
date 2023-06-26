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
import com.dwiki.tiketku.databinding.FragmentDetailPergiBinding
import com.dwiki.tiketku.util.Utill
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.dwiki.tiketku.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailPergiFragment : Fragment() {

    private lateinit var binding: FragmentDetailPergiBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val berandaViewModel: BerandaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailPergiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idDeparture = berandaViewModel.getIdDep()
        detailViewModel.detailTicket(idDeparture!!)
        detailViewModel.getDetailTicket.observe(viewLifecycleOwner) { detailTicket ->

            val getDetail = detailTicket.data
            if (getDetail != null){
                binding.layoutCvDetail.visibility = View.VISIBLE
                Log.d("DetailPenerbangan","Berhasil get data")
                binding.apply {
                    val timeTakeoff = getDetail.dateTakeoff
                    val timeLanding = getDetail.dateLanding

                    val takeOffSplit = timeTakeoff?.split(":")
                    val landingSplit = timeLanding?.split(":")

                    val takeOffHour = takeOffSplit!![0].toInt()
                    val takeOffMinute = takeOffSplit!![1].toInt()

                    val landingHour = landingSplit!![0].toInt()
                    val landingMinute = landingSplit!![1].toInt()

                    var hourDiff = landingHour - takeOffHour
                    var minuteDiff = landingMinute - takeOffMinute

                    if (minuteDiff < 0) {
                        hourDiff -= 1
                        minuteDiff += 60
                    }

                    if (hourDiff < 0) {
                        hourDiff += 24
                    }

                    txtLamaPerjalanan.text = "(${hourDiff}h ${minuteDiff}m)"
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