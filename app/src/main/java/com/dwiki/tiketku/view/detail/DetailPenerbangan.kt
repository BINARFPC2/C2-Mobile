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
        detailViewModel.getDetailTicket(id!!).observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING->{
                    Log.d("Detail Fragment","Loading")
                }
                Status.SUCCESS->{
                    val detailTicket = it.data
                    if (detailTicket != null){
                        binding.apply {
                            txtBandaraAwal.text = detailTicket.airportFrom
                            txtBandaraTujuan.text = detailTicket.airportTo
                            txtKeberangkatan.text = detailTicket.cityFrom
                            txtTujuan.text = detailTicket.cityTo
                            txtJamBerangkat.text = detailTicket.dateTakeoff
                            txtTanggalBerangkat.text = detailTicket.dateDeparture
                            tvPesawat.text = detailTicket.airlines
                            tvInformasi.text = detailTicket.information
                            txtJamDatang.text = detailTicket.dateLanding
                            txtTanggalSampai.text = detailTicket.dateReturn
                            val price = Utill.getPriceIdFormat(detailTicket.price)
                            txtHargaTotal.text = "$price/pax"
                        }
                    }

                }
                Status.ERROR->{
                    Log.e("Detail Fragment",it.message.toString())
                }
            }
        }
    }

}