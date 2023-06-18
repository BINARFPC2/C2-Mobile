package com.dwiki.tiketku.view.beranda

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.DestinasiFavoritAdapter
import com.dwiki.tiketku.databinding.FragmentBerandaBinding
import com.dwiki.tiketku.util.Status
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.dwiki.tiketku.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import kotlin.math.log


@AndroidEntryPoint
class BerandaFragment : Fragment() {


    private lateinit var binding: FragmentBerandaBinding
    private val berandaViewModel:BerandaViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
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
        activity?.window!!.statusBarColor = ContextCompat.getColor(requireContext(),R.color.darkblue_02)

        berandaViewModel.getdestinasiFavResult()
        berandaViewModel.destinasiResult.observe(viewLifecycleOwner){ data ->
            binding.rvDestinasiFavorit.apply {
                        layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                        adapterfavDestinasi = DestinasiFavoritAdapter(data.data ?: emptyList())
                        adapter = adapterfavDestinasi
                    }
        }

        val token  = loginViewModel.getTokenPreferences()
        loginViewModel.getLoginState().observe(viewLifecycleOwner){
            Log.d("Beranda Fragment","$it")
        }
        Log.d("Beranda Fragment","$token")

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

        binding.layoutDeparture.setOnClickListener {
            val bundle =Bundle()
            bundle.putString("from","from")
            findNavController().navigate(R.id.action_berandaFragment_to_pilihDestnasiFragment,bundle)
        }

        binding.layoutTujuan.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("to","to")
            findNavController().navigate(R.id.action_berandaFragment_to_pilihDestnasiFragment,bundle)
        }





        binding.btnCari.setOnClickListener {

            val cityFrom = berandaViewModel.getCityFrom()
            val cityTo = berandaViewModel.getCityTo()
            val typeSeat = berandaViewModel.getNamaKelas()

            berandaViewModel.ticketsBeranda(cityFrom!!,cityTo!!,typeSeat!!)
            berandaViewModel.getTicketsBeranda.observe(viewLifecycleOwner){tickets ->
                val listTicket = tickets.size
                if (listTicket != 0){
                    val dewasa = berandaViewModel.getPenumpangDewasa()
                    val anak = berandaViewModel.getPenumpangAnak()
                    val bayi = berandaViewModel.getPenumpangBayi()
                    val totalPassengers = dewasa + anak + bayi

                    for (ticket in tickets.indices){
                        val idTicket = tickets[ticket].id
                        val dateDeparture = berandaViewModel.getDepartureDate()
                        val dateReturn = berandaViewModel.getDatePref()
                        berandaViewModel.updateTicket(tickets[ticket].id,dateDeparture!!,dateReturn!!,totalPassengers)
                        berandaViewModel.getResponseUpdateTicket.observe(viewLifecycleOwner){
                            if (it == "Update Data Ticket Successfully") {
                                if (findNavController().currentDestination?.id == R.id.berandaFragment){
                                    findNavController().navigate(R.id.action_berandaFragment_to_hasilPencarianFragment)
                                }
                            }
                        }
                    }
                } else {
                    findNavController().navigate(R.id.action_berandaFragment_to_hasilPencarianEmptyFragment)
                }
            }



        }

        flipLokasi()
        getKelasPenerbangan()
        getSetPenumpang()
        getCityFrom()
        getCityTo()
        datePickerReturn()
        datePickerDeparture()


    }

    private fun getCityTo() {
        val destination = berandaViewModel.getCityTo()
        binding.tvTujuan.text = destination
    }

    private fun getCityFrom() {
        val departure = berandaViewModel.getCityFrom()
        binding.tvDeparture.text =  departure
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
                val month = datePicker.month
                val tahunDeparture = datePicker.year
                val hariDeparture = datePicker.dayOfMonth
                val tanggalDeparture = "$tahunDeparture-$month-$hariDeparture"
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
                val month = datePicker.month
                val tahun = datePicker.year
                val hari = datePicker.dayOfMonth
                val tanggalPilihan = "$tahun-$month-$hari"
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