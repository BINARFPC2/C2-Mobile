package com.dwiki.tiketku.view.beranda

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentBerandaBinding
import com.dwiki.tiketku.databinding.FragmentRiwayat2Binding
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class BerandaFragment : Fragment() {


    private lateinit var binding: FragmentBerandaBinding
    private val berandaViewModel:BerandaViewModel by viewModels()
    private var kelas:String? = null
    private var jumlahPenumpang:Int? = null
    private var tanggalKembali = "Tanggal"
    private var tanggalPergi:String? = null
    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBerandaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val dateNow = berandaViewModel.getDatePref()
        binding.tvPilihTanggal.text = dateNow




        binding.tvPenumpang.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment_to_bottomSheetSetPenumpang)
        }

        binding.tvClass.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment_to_bottomSheetKelasFragment)
        }

        

        binding.btnFlip.setOnClickListener {
            val keberangkatan = binding.tvDeparture.text.toString()
            val tujuan = binding.tvTujuan.text.toString()
            binding.tvTujuan.text = keberangkatan
            binding.tvDeparture.text = tujuan
        }

        getKelasPenerbangan()
//        getTanggalKembali()
        getTanggalBerangkat()
        getSetPenumpang()
        datePickerReturn()
        datePickerDeparture()

    }

    private fun datePickerDeparture() {
        binding.tvDepartureDate.setOnClickListener {
            val nameMonth = ArrayList<String>()
            nameMonth.add("Januari")
            nameMonth.add("Februari")
            nameMonth.add("Maret")
            nameMonth.add("April")
            nameMonth.add("Mei")
            nameMonth.add("Juni")
            nameMonth.add("Juli")
            nameMonth.add("Agustus")
            nameMonth.add("September")
            nameMonth.add("Oktober")
            nameMonth.add("November")
            nameMonth.add("Desember")

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),R.style.DateDialogTheme,
                { _, year, month, dayOfMonth ->
                    val bulan =nameMonth[month]
                    tanggalPergi = "$dayOfMonth $bulan $year"
                    binding.tvDepartureDate.setText(tanggalPergi)
                    binding.tvDepartureDate.setTextColor(resources.getColor(R.color.black))
                },
                year, month, day,
            )
            datePickerDialog.show()
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.darkblue_05))
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.darkblue_05))
        }
    }

    private fun datePickerReturn(){

        binding.tvPilihTanggal.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment_to_datePickerDialog)
        }
    }


//    private fun getTanggalKembali() {
//
//        berandaViewModel.date.observe(viewLifecycleOwner){
//            Log.d("Beranda Fragment", it)
//            if (it.isEmpty()) {
//                binding.tvPilihTanggal.text = defaultTanggal
//                binding.tvPenumpang.setTextColor(resources.getColor(R.color.darkblue_05))
//            } else {
//                binding.tvPilihTanggal.text = it
//                binding.tvPenumpang.setTextColor(resources.getColor(R.color.black))
//
//            }
//        }
//    }

    private fun getTanggalBerangkat() {
        if (tanggalPergi == null) {
            binding.tvDepartureDate.setText("Pilih Tanggal")
            binding.tvDepartureDate.setTextColor(resources.getColor(R.color.darkblue_05))
        } else {
            binding.tvDepartureDate.setText(tanggalPergi)
            binding.tvPenumpang.setTextColor(resources.getColor(R.color.black))

        }
    }



    private fun getKelasPenerbangan() {
       val kelas = berandaViewModel.getNamaKelas()
        binding.tvClass.text = kelas


    }

    private fun getSetPenumpang(){
       val dewasa = berandaViewModel.getPenumpangDewasa()
        val anak = berandaViewModel.getPenumpangAnak()
        val bayi = berandaViewModel.getPenumpangBayi()
        val total = dewasa + anak + bayi
        binding.tvPenumpang.text = "$total Penumpang"
    }

    override fun onResume() {
        super.onResume()
        Log.d("Beranda Fragment", "onResume")
    }
    override fun onDestroy() {
        super.onDestroy()
        berandaViewModel.clearData()
        Log.d("Beranda Fragment", "onDestroy")
    }




}