package com.dwiki.tiketku.view.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.security.identity.MessageDecryptionException
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentBottomSheetSetPenumpangBinding
import com.dwiki.tiketku.model.penumpang.PenumpangData
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.dwiki.tiketku.viewmodel.TestViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetSetPenumpang : BottomSheetDialogFragment() {

    private lateinit var binding:FragmentBottomSheetSetPenumpangBinding
    private val berandaViewModel: BerandaViewModel by viewModels()
    private val testViewModel:TestViewModel by viewModels()



    var tiketDewasa= 0
    var tiketAnak = 0
    var tiketBayi = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetSetPenumpangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("Sheet set Penumpang", "onViewCreated")
        setClose()

            val jmlDewasa = berandaViewModel.getPenumpangDewasa()
            val jmlAnak = berandaViewModel.getPenumpangAnak()
            val jmlBayi = berandaViewModel.getPenumpangBayi()

            val dewasa = setDewasa(jmlDewasa)
            val anak = setAnak(jmlAnak)
            val bayi = setBayi(jmlBayi)


            binding.tvPassengerBayi.text = bayi.toString()
            binding.tvPassenger.text = dewasa.toString()
            binding.tvPassengerAnak.text = anak.toString()



        binding.btnSimpan.setOnClickListener {
            val tvDewasa = binding.tvPassenger.text.toString()
            val tvBayi = binding.tvPassengerBayi.text.toString()
            val tvAnak = binding.tvPassengerAnak.text.toString()
            berandaViewModel.savePenumpangPreferences(tvDewasa.toInt(), tvAnak.toInt(), tvBayi.toInt())
//            val dataPenumpang = PenumpangData("Darman1","mr2","darman1@gmail.com")
//            testViewModel.addData(dataPenumpang)
//            val penumpangDewasa = ArrayList<PenumpangData>()
//            val penumpangDewasa = (PenumpangData("Darman2","mr2","darman2@gmail.com"))
//            berandaViewModel.getPenumpangData(penumpangDewasa)
            findNavController().navigate(R.id.action_bottomSheetSetPenumpang_to_berandaFragment)

        }

    }

    private fun setClose() {
        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    private fun setBayi(tiketBayi:Int):Int {

            binding.tvPassengerBayi.text = tiketBayi.toString()
            var tiketBayi1 = tiketBayi
            binding.btnIncreaseBayi.setOnClickListener {
                tiketBayi1 += 1
                binding.tvPassengerBayi.text = tiketBayi1.toString()
            }

            binding.btnDecreaseBayi.setOnClickListener {
                if (tiketBayi1 > 0) {
                    tiketBayi1 -= 1
                    binding.tvPassengerBayi.text = tiketBayi1.toString()
                }
            }
        return tiketBayi1
    }

    private fun setAnak(tiketAnak:Int):Int{
        binding.tvPassengerAnak.text = tiketAnak.toString()
        var tiketAnak1 = tiketAnak
        binding.btnIncreaseAnak.setOnClickListener {
            tiketAnak1 += 1
            binding.tvPassengerAnak.text = tiketAnak1.toString()
        }

        binding.btnDecreaseAnak.setOnClickListener {
            if (tiketAnak1 > 0) {
                tiketAnak1 -= 1
                binding.tvPassengerAnak.text = tiketAnak1.toString()
            }
        }
        return tiketAnak1
    }

    private fun setDewasa(tiketDewasa:Int):Int{
        binding.tvPassenger.text = tiketDewasa.toString()

        var tiketDewasa1 = tiketDewasa
        binding.btnIncrease.setOnClickListener {
            tiketDewasa1 += 1
            binding.tvPassenger.text = tiketDewasa1.toString()

        }

        binding.btnDecrease.setOnClickListener {
            if (tiketDewasa1 > 0) {
                tiketDewasa1 -= 1
                binding.tvPassenger.text = tiketDewasa1.toString()
            }
        }
        return tiketDewasa1
    }

    override fun onResume() {
        super.onResume()

        val jmlDewasa = berandaViewModel.getPenumpangDewasa()
        val jmlAnak = berandaViewModel.getPenumpangAnak()
        val jmlBayi = berandaViewModel.getPenumpangBayi()

        val dewasa = setDewasa(jmlDewasa)
        val anak = setAnak(jmlAnak)
        val bayi = setBayi(jmlBayi)


        binding.tvPassengerBayi.text = bayi.toString()
        binding.tvPassenger.text = dewasa.toString()
        binding.tvPassengerAnak.text = anak.toString()

        binding.btnSimpan.setOnClickListener {
            val tvDewasa = binding.tvPassenger.text.toString()
            val tvBayi = binding.tvPassengerBayi.text.toString()
            val tvAnak = binding.tvPassengerAnak.text.toString()
            berandaViewModel.savePenumpangPreferences(tvDewasa.toInt(), tvAnak.toInt(), tvBayi.toInt())
            findNavController().navigate(R.id.action_bottomSheetSetPenumpang_to_berandaFragment)
        }
    }

}