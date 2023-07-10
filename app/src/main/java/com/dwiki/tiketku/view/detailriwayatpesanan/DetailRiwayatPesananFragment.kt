package com.dwiki.tiketku.view.detailriwayatpesanan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.RiwayatAdapter
import com.dwiki.tiketku.adapter.RiwayatPenumpangAdapter
import com.dwiki.tiketku.databinding.FragmentDetailRiwayatPesananBinding
import com.dwiki.tiketku.databinding.ItemRiwayatPesananBinding
import com.dwiki.tiketku.util.Utill
import com.dwiki.tiketku.viewmodel.LoginViewModel
import com.dwiki.tiketku.viewmodel.RiwayatViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailRiwayatPesananFragment : Fragment() {

    private lateinit var binding: FragmentDetailRiwayatPesananBinding
    private val loginViewModel:LoginViewModel by viewModels()
    private val riwayatViewModel:RiwayatViewModel by viewModels()
    private lateinit var riwayatAdapter:RiwayatPenumpangAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailRiwayatPesananBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window!!.statusBarColor = ContextCompat.getColor(requireContext(),R.color.darkblue_05)

        val token = loginViewModel.getTokenPreferences()
        val args = arguments?.getString("idRiwayat")
        riwayatViewModel.detailRiwayat(token!!,args!!)
      riwayatViewModel.getDetailRiwayat.observe(viewLifecycleOwner){
         val itemdetail = it.data[0]
          if (itemdetail.returnTicket != null){
              binding.apply {
                  cvReturn.visibility = View.VISIBLE

                  val returnTicket = itemdetail.returnTicket
                  Glide.with(requireContext()).load(returnTicket.logo).into(imgLogoMaskapaiReturn)
                  tvCodePlaneReturn.text = returnTicket.code
                  tvBookingCodeReturn.text = returnTicket.bookingCode
                  txtJamBerangkatReturn.text = returnTicket.dateTakeoff
                  txtBandaraAwalReturn.text = returnTicket.airportFrom
                  txtBandaraTujuanReturn.text = returnTicket.airportTo
                  txtJamBerangkatReturn.text = returnTicket.dateTakeoff
                  txtJamDatangReturn.text = returnTicket.dateLanding
                  txtTanggalBerangkatReturn.text = returnTicket.dateDeparture
                  txtTanggalDatangReturn.text = returnTicket.dateEnd
                  tvPesawatReturn.text = returnTicket.airlines
//                  tvInformasiReturn.text =returnTicket.information
                  rvPenumpaangReturn.apply {
                      layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                      riwayatAdapter = RiwayatPenumpangAdapter(itemdetail.passengers)
                      adapter = riwayatAdapter
                  }
                  txtJamDatangReturn.text = returnTicket.dateLanding


                  val departure = itemdetail.departureTicket
                  Glide.with(requireContext()).load(departure.logo).into(imgLogoMaskapai)
                  tvCodePlane.text = departure.code
                  tvBookingCode.text = departure.bookingCode
                  txtJamBerangkat.text = departure.dateTakeoff
                  txtBandaraAwal.text = departure.airportFrom
                  txtBandaraTujuan.text = departure.airportTo
                  txtJamBerangkat.text = departure.dateTakeoff
                  txtJamDatang.text = departure.dateLanding
                  txtTanggalBerangkat.text = departure.dateDeparture
                  txtTanggalDatang.text = departure.dateEnd
                  tvPesawat.text = departure.airlines
//                  tvInformasi.text = departure.information
                  rvPenumpaang.apply {
                      layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                      riwayatAdapter = RiwayatPenumpangAdapter(itemdetail.passengers)
                      adapter = riwayatAdapter
                  }
                  txtJamDatang.text = departure.dateLanding
              }
          } else {
              binding.apply {

                  val departure = itemdetail.departureTicket
                  Glide.with(requireContext()).load(departure.logo).into(imgLogoMaskapai)
                  tvCodePlane.text = departure.code
                  tvBookingCode.text = departure.bookingCode
                  txtJamBerangkat.text = departure.dateTakeoff
                  txtBandaraAwal.text = departure.airportFrom
                  txtBandaraTujuan.text = departure.airportTo
                  txtJamBerangkat.text = departure.dateTakeoff
                  txtJamDatang.text = departure.dateLanding
                  txtTanggalBerangkat.text = departure.dateDeparture
                  txtTanggalDatang.text = departure.dateEnd
                  tvPesawat.text = departure.airlines
                  rvPenumpaang.apply {
                      layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                      riwayatAdapter = RiwayatPenumpangAdapter(itemdetail.passengers)
                      adapter = riwayatAdapter
                  }
                  txtJamDatang.text = departure.dateLanding
              }
          }

          binding.txtTiketDewasa.text = "${itemdetail.totalPassenger} Penumpang "
          val idrPrice = Utill.getPriceIdFormat(itemdetail.totalPrice)
          binding.txtTotalPembayaran.text = idrPrice
      }
    }

}