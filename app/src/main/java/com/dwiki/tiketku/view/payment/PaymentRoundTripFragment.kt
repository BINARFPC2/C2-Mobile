package com.dwiki.tiketku.view.payment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentPaymenRoundTripBinding
import com.dwiki.tiketku.util.Utill
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.dwiki.tiketku.viewmodel.DetailViewModel
import com.dwiki.tiketku.viewmodel.LoginViewModel
import com.dwiki.tiketku.viewmodel.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PaymentRoundTripFragment : Fragment() {

    private lateinit var binding: FragmentPaymenRoundTripBinding
    private val detailViewModel: DetailViewModel by viewModels()
    private val berandaViewModel: BerandaViewModel by viewModels()
    private val paymentViewModel: PaymentViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymenRoundTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val idDep = berandaViewModel.getIdDep()
        val idReturn = berandaViewModel.getIdReturn()
        val totalPrice = berandaViewModel.getPriceTotalTwo()

        //detail ticket departure
        ticketDeparture(idDep, totalPrice)

        //detail ticket return
        ticketReturn(idReturn, totalPrice)


        binding.btnBayar.setOnClickListener {
            val cardNumber = binding.etCardNumber.text.toString()
            val cvv = binding.etCVV.text.toString()
            val cardHolderName = binding.etCardName.text.toString()
            val expDate = binding.etExpiryDate.text.toString()
            val token = loginViewModel.getTokenPreferences()

            Log.d("Payment Fragment","$token")
            paymentViewModel.postPayment("Bearer $token",cardNumber,cardHolderName,cvv.toInt(),expDate)
            paymentViewModel.getPayment.observe(viewLifecycleOwner){
                if (it == "Success"){
                    findNavController().navigate(R.id.action_paymentRoundTripFragment_to_bottomSheetSuksesBayarFragment)
                }
            }
        }
    }

    private fun ticketReturn(idReturn: String?, totalPrice: Int) {
        detailViewModel.detailTicketReturn(idReturn!!)
        detailViewModel.getDetailTicketReturn.observe(viewLifecycleOwner) {
            binding.apply {
                val dewasa = berandaViewModel.getPenumpangDewasa()
                val anak = berandaViewModel.getPenumpangAnak()
                val bayi = berandaViewModel.getPenumpangBayi()
                val total = dewasa + anak + bayi
                binding.tvHarga.text = Utill.getPriceIdFormat(totalPrice)
                binding.tvPassenger.text = "Dewasa : ${dewasa}, Anak : ${anak}, Bayi : ${bayi}"

                val detailReturn = it.data
                if (detailReturn != null) {
                    val timeTakeoff = detailReturn.dateTakeoff
                    val timeLanding = detailReturn.dateLanding

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

                    binding.keberangkatan.text = detailReturn.cityFrom
                    binding.tglKeberangkatan.text = detailReturn.dateDeparture
                    binding.jamKeberangkatan.text = detailReturn.dateTakeoff
                    binding.kedatangan.text = detailReturn.cityTo
                    binding.tglKedatangan.text = detailReturn.dateEnd
                    binding.jamKedatangan.text = detailReturn.dateLanding
                    binding.tvCode.text = detailReturn.bookingCode
                    binding.tvDurasi.text = "(${hourDiff}h ${minuteDiff}m)"
                }
            }
        }

        binding.btnDetailCard.setOnClickListener {
            binding.layoutKreditCard.visibility = View.VISIBLE
        }
    }

    private fun ticketDeparture(idDep: String?, totalPrice: Int) {
        detailViewModel.detailTicket(idDep!!)
        detailViewModel.getDetailTicket.observe(viewLifecycleOwner) {
            binding.apply {
                val dewasa = berandaViewModel.getPenumpangDewasa()
                val anak = berandaViewModel.getPenumpangAnak()
                val bayi = berandaViewModel.getPenumpangBayi()
                val total = dewasa + anak + bayi
                binding.tvHarga.text = Utill.getPriceIdFormat(totalPrice)
                binding.tvPassenger.text = "Dewasa : ${dewasa}, Anak : ${anak}, Bayi : ${bayi}"

                val detailDeparture = it.data
                if (detailDeparture != null) {
                    val timeTakeoff = detailDeparture.dateTakeoff
                    val timeLanding = detailDeparture.dateLanding

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

                    binding.keberangkatanReturn.text = detailDeparture.cityFrom
                    binding.tglKeberangkatanReturn.text = detailDeparture.dateDeparture
                    binding.jamKeberangkatanReturn.text = detailDeparture.dateTakeoff
                    binding.kedatanganReturn.text = detailDeparture.cityTo
                    binding.tglKedatanganReturn.text = detailDeparture.dateEnd
                    binding.jamKedatanganReturn.text = detailDeparture.dateLanding
                    binding.tvDurasiReturn.text = "(${hourDiff}h ${minuteDiff}m)"
                    binding.tvCodeReturn.text = detailDeparture.bookingCode
                    binding.tvClass.text = detailDeparture.typeSeat
                }
            }
        }
    }

    companion object{
        private val TAB_TITLES = intArrayOf(
            R.string.str_pergi,
            R.string.str_pulang
        )
    }

}