package com.dwiki.tiketku.view.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentCheckoutBinding
import com.dwiki.tiketku.util.Utill
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.dwiki.tiketku.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class checkout : Fragment() {

    private lateinit var binding:FragmentCheckoutBinding
    private val detailViewModel:DetailViewModel by viewModels()
    private val berandaViewModel:BerandaViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ticketId =  berandaViewModel.getIdTicket()
        detailViewModel.detailTicket(ticketId!!)
        detailViewModel.getDetailTicket.observe(viewLifecycleOwner){
            binding.apply {
               val detailTicket = it.data
                if (detailTicket != null){
                    val timeTakeoff = detailTicket.dateTakeoff
                    val timeLanding = detailTicket.dateLanding

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

                    txtKeberangkatan.text = detailTicket.cityFrom
                    txtTujuan.text = detailTicket.cityTo
                    txtJamBerangkat.text = detailTicket.dateTakeoff
                    txtTanggalBerangkat.text = detailTicket.dateDeparture
                    txtBandaraAwal.text = detailTicket.airportFrom
                    tvPesawat.text =detailTicket.airlines
                    tvInformasi.text = detailTicket.information
                    txtJamDatang.text = detailTicket.dateLanding
                    txtTanggalSampai.text = detailTicket.dateEnd
                    txtBandaraTujuan.text = detailTicket.airportTo

                    val dewasa = berandaViewModel.getPenumpangDewasa()
                    val anak = berandaViewModel.getPenumpangAnak()
                    val bayi = berandaViewModel.getPenumpangBayi()
                    val total = dewasa + anak + bayi
                    val price = detailTicket.price
                    val totalHarga = price?.times(total)
                    val totalIdr = Utill.getPriceIdFormat(totalHarga!!)

                    txtHargaTiketDewasa.text = totalIdr
                    txtTiketDewasa.text = "$total Penumpang"
                    txtTotalPembayaran.text = totalIdr
                    berandaViewModel.saveTotalOne(totalHarga)

                }
            }
        }

        binding.btnCheckout.setOnClickListener {

            findNavController().navigate(R.id.action_checkout2_to_paymentFragment)
        }
    }

}