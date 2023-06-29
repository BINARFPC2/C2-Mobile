package com.dwiki.tiketku.view.payment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentPaymentBinding
import com.dwiki.tiketku.util.Utill
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.dwiki.tiketku.viewmodel.DetailViewModel
import com.dwiki.tiketku.viewmodel.LoginViewModel
import com.dwiki.tiketku.viewmodel.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PaymentFragment : Fragment() {

    private lateinit var binding:FragmentPaymentBinding
    private val detailViewModel:DetailViewModel by viewModels()
    private val berandaViewModel:BerandaViewModel by viewModels()
    private val paymentViewModel: PaymentViewModel by viewModels()
    private val loginViewModel:LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idTicket = berandaViewModel.getIdTicket()
        val hargaTotal = berandaViewModel.getPriceTotalOne()
        detailViewModel.detailTicket(idTicket!!)
        detailViewModel.getDetailTicket.observe(viewLifecycleOwner){
            binding.apply {
                val dewasa = berandaViewModel.getPenumpangDewasa()
                val anak = berandaViewModel.getPenumpangAnak()
                val bayi = berandaViewModel.getPenumpangBayi()
                val total = dewasa + anak + bayi
                binding.tvHarga.text = Utill.getPriceIdFormat(hargaTotal)
                binding.tvPassenger.text = "Dewasa : ${dewasa}, Anak : ${anak}, Bayi : ${bayi}"

                val getDetail = it.data

                if (getDetail != null) {
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

                    binding.keberangkatan.text = getDetail.cityFrom
                    binding.tglKeberangkatan.text = getDetail.dateDeparture
                    binding.jamKeberangkatan.text = getDetail.dateTakeoff
                    binding.kedatangan.text = getDetail.cityTo
                    binding.tglKedatangan.text = getDetail.dateEnd
                    binding.jamKedatangan.text = getDetail.dateLanding
                    binding.tvDurasi.text = "(${hourDiff}h ${minuteDiff}m)"
                    binding.tvCode.text = getDetail.bookingCode
                    binding.tvClass.text = getDetail.typeSeat


                }
            }
        }


        binding.btnDetailCard.setOnClickListener {
            binding.layoutKreditCard.visibility =View.VISIBLE
        }

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
                    findNavController().navigate(R.id.action_paymentFragment_to_bottomSheetSuksesBayarFragment)
                }
            }
        }
    }

}