package com.dwiki.tiketku.view.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentDetailPenerbanganBinding
import com.dwiki.tiketku.model.ticket.DataItemTicket
import com.dwiki.tiketku.model.ticket.DetailTicket
import com.dwiki.tiketku.util.Status
import com.dwiki.tiketku.util.Utill
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.dwiki.tiketku.viewmodel.BiodataViewModel
import com.dwiki.tiketku.viewmodel.DetailViewModel
import com.dwiki.tiketku.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPenerbangan : Fragment() {

    private lateinit var binding:FragmentDetailPenerbanganBinding
    private val detailViewModel:DetailViewModel by viewModels()
    private val loginViewModel:LoginViewModel by viewModels()
    private val berandaViewModel:BerandaViewModel by viewModels()

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
        val idTicket = arguments?.getString("id")

        detailViewModel.detailTicket(idTicket!!)
        detailViewModel.getDetailTicket.observe(viewLifecycleOwner) { detailTicket ->
            val getDetail = detailTicket.data
            if (getDetail != null){
                binding.layoutCvDetail.visibility = View.VISIBLE
                binding.layoutTotal.visibility = View.VISIBLE
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
                    txtHargaTotal.text = "$price/pax"
                }
            } else {
                Log.e("DetailPenerbangan", "detailTicket is null")
            }

        }

        binding.btnPilih.setOnClickListener {
            berandaViewModel.saveIdTicket(idTicket)
            loginViewModel.getLoginState().observe(viewLifecycleOwner){
                if (it) {
                    if (findNavController().currentDestination!!.id == R.id.detailPenerbangan){
                        findNavController().navigate(R.id.action_detailPenerbangan_to_biodataPemesan)
                    }

                } else {
                    val fragId = findNavController().currentDestination?.id
                    findNavController().popBackStack(fragId!!,true)
                    findNavController().navigate(R.id.bottomSheetCheckUserLogin)
                }

                }

            }
        }

}