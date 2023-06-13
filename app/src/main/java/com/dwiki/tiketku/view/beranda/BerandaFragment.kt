package com.dwiki.tiketku.view.beranda

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.DestinasiFavoritAdapter
import com.dwiki.tiketku.databinding.FragmentBerandaBinding
import com.dwiki.tiketku.util.Status
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar


@AndroidEntryPoint
class BerandaFragment : Fragment() {


    private lateinit var binding: FragmentBerandaBinding
    private val berandaViewModel:BerandaViewModel by viewModels()
    private lateinit var adapterfavDestinasi:DestinasiFavoritAdapter
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

        berandaViewModel.destinasiResult.observe(viewLifecycleOwner){ data ->
            binding.rvDestinasiFavorit.apply {
                        layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                        adapterfavDestinasi = DestinasiFavoritAdapter(data.data ?: emptyList())
                        adapter = adapterfavDestinasi
                    }
        }


//        berandaViewModel.destinasiFavResult().observe(requireActivity()){
//            when(it.status){
//                Status.SUCCESS->{
//                    binding.rvDestinasiFavorit.apply {
//                        layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
//                        adapterfavDestinasi = DestinasiFavoritAdapter(it.data?.data ?: emptyList())
//                        adapter = adapterfavDestinasi
//                    }
//                }
//                Status.LOADING ->{
//                    Log.d("Beranda Fragment","Loading")
//                }
//                Status.ERROR ->{
//                    Log.d("Beranda Fragment","Error")
//                }
//            }
//        }

        //get date
        val dateNowReturn = berandaViewModel.getDatePref()
        val dateNowDeparture = berandaViewModel.getDepartureDate()
        //set date
        binding.tvPilihTanggal.text = dateNowReturn
        binding.tvDepartureDate.text = dateNowDeparture


        binding.tvPenumpang.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment_to_bottomSheetSetPenumpang)
        }

        binding.tvClass.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment_to_bottomSheetKelasFragment)
        }





        binding.btnCari.setOnClickListener {
            //test get data
            val departureDate = berandaViewModel.getDepartureDate()
            val returnDate = berandaViewModel.getDatePref()
            //get berdasarkan jenis passengers
            val dewasa = berandaViewModel.getPenumpangDewasa()
            val anak = berandaViewModel.getPenumpangAnak()
            val bayi = berandaViewModel.getPenumpangBayi()
            val total = dewasa + anak + bayi

            val passengers = total
            val kelas = berandaViewModel.getNamaKelas()
            Log.d("Beranda Fragment","$departureDate $returnDate $passengers $kelas")
        }

        flipLokasi()
        getKelasPenerbangan()
        getSetPenumpang()
        datePickerReturn()
        datePickerDeparture()

    }



    private fun flipLokasi() {
        binding.btnFlip.setOnClickListener {
            val keberangkatan = binding.tvDeparture.text.toString()
            val tujuan = binding.tvTujuan.text.toString()
            binding.tvTujuan.text = keberangkatan
            binding.tvDeparture.text = tujuan
        }
    }

    private fun datePickerDeparture() {
        binding.tvDepartureDate.setOnClickListener {
            val nameMonth = namaMonth()
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
                },
                year, month, day,
            )
            datePickerDialog.show()
            datePickerDialog.setOnDateSetListener { datePicker, _, _, _ ->
                val bulanDeparture = nameMonth[datePicker.month]
                val tahunDeparture = datePicker.year
                val hariDeparture = datePicker.dayOfMonth
                val tanggalDeparture = "$hariDeparture $bulanDeparture $tahunDeparture"
                berandaViewModel.saveDepartureDate(tanggalDeparture)
                findNavController().navigate(R.id.berandaFragment)
            }
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.darkblue_05))
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.darkblue_05))
        }
    }

    private fun datePickerReturn(){
        binding.tvPilihTanggal.setOnClickListener {

            val nameMonth = namaMonth()

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(),R.style.DateDialogTheme, { _, year, month, dayOfMonth ->
                    val bulan =nameMonth[month]
                    tanggalPergi = "$dayOfMonth $bulan $year"
                    binding.tvPilihTanggal.text = tanggalPergi
                },
                year, month, day,
            )
            datePickerDialog.show()
            datePickerDialog.setOnDateSetListener { datePicker, _, _, _ ->
                val nameMonth = namaMonth()
                val bulan =nameMonth[datePicker.month]
                val tahun = datePicker.year
                val hari = datePicker.dayOfMonth
                val tanggalPilihan = "$hari $bulan $tahun"
                berandaViewModel.saveDatePref(tanggalPilihan)
                findNavController().navigate(R.id.berandaFragment)
            }
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.darkblue_05))
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.darkblue_05))
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

    private fun namaMonth(): ArrayList<String> {
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
        return nameMonth
    }


    override fun onResume() {
        super.onResume()
        berandaViewModel.destinasiResult.observe(viewLifecycleOwner){ data ->
            binding.rvDestinasiFavorit.apply {
                layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                adapterfavDestinasi = DestinasiFavoritAdapter(data.data ?: emptyList())
                adapter = adapterfavDestinasi
            }
        }

    }


}