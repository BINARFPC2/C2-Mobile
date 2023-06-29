package com.dwiki.tiketku.view.hasilpencarianempty

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.TicketAdapter
import com.dwiki.tiketku.databinding.FragmentHasilPencarianReturnBinding
import com.dwiki.tiketku.util.Utill
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.dwiki.tiketku.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*

@AndroidEntryPoint
class HasilPencarianReturnFragment : Fragment() {

    private lateinit var binding:FragmentHasilPencarianReturnBinding
    private val berandaViewModel: BerandaViewModel by viewModels()
    private val detailViewModel:DetailViewModel by viewModels()
    private lateinit var ticketAdapter:TicketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHasilPencarianReturnBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window!!.statusBarColor = ContextCompat.getColor(requireContext(),R.color.darkblue_05)
        val cityFrom = berandaViewModel.getCityFrom()
        val cityTo = berandaViewModel.getCityTo()
        val dewasa = berandaViewModel.getPenumpangDewasa()
        val anak = berandaViewModel.getPenumpangAnak()
        val bayi = berandaViewModel.getPenumpangBayi()
        val dateDeparture = berandaViewModel.getDepartureDate()
        val dateRetun = berandaViewModel.getDatePref()
        val totalPassengers = dewasa + anak + bayi
        val seatClass = berandaViewModel.getNamaKelas()

        binding.tvToolbar.text = "$cityFrom <> $cityTo - $totalPassengers Penumpang - $seatClass"

//        val idDeparture = arguments?.getString("idDep")
        val idDep = berandaViewModel.getIdDep()
        Log.d("Hasil Pencarian Return",idDep!!)

        dateToolbarDeparture(dateDeparture)
        dateToolbarReturn(dateRetun)
        returnOnly(cityFrom, cityTo, seatClass, dateRetun)
        getDepartureTicket(idDep)

        binding.btnGanti.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.hasilPencarianReturnFragment) {
//                val fragId = findNavController().currentDestination?.id
//                findNavController().popBackStack(fragId!!,true)
                findNavController().navigate(R.id.action_hasilPencarianReturnFragment_to_hasilPencarianPFragment)
            }

        }

    }

    private fun getDepartureTicket(idDeparture: String?) {
        detailViewModel.detailTicket(idDeparture!!)
        detailViewModel.getDetailTicket.observe(viewLifecycleOwner){it ->

            Log.d("Hasil Pencarian Return",it.toString())
            val dataItemTicket = it.data

            val timeTakeoff = dataItemTicket?.dateTakeoff
            val timeLanding = dataItemTicket?.dateLanding

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

            binding.tvDurasi.text = "${hourDiff}h ${minuteDiff}m"
            binding.tvJamKeberangkatan.text = dataItemTicket.dateTakeoff
            binding.tvJamSampai.text = dataItemTicket.dateLanding
            binding.tvKotaKeberangkatan.text = dataItemTicket.cityFrom
            binding.tvKotaSampai.text = dataItemTicket.cityTo
//            val getPrice = arguments?.getInt("pricePergi")
            val getPrice = dataItemTicket.price
            val price = Utill.getPriceIdFormat(getPrice!!)
            binding.tvHarga.text = price
            binding.tvPesawat.text = dataItemTicket.airlines
        }
    }

    private fun dateToolbarDeparture(dateDeparture: String?) {
        if (dateDeparture != null) {
            binding.etDate.setText(dateDeparture)
            binding.etDate.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireContext(), R.style.DateDialogTheme,
                    { _, year, month, dayOfMonth ->

                        val tanggalPergi = "$year-${month + 1}-$dayOfMonth"
                        binding.etDate.setText(tanggalPergi)
                    },
                    year, month, day,
                )
                datePickerDialog.show()
                datePickerDialog.setOnDateSetListener { datePicker, _, _, _ ->
                    val month = datePicker.month
                    val tahunDeparture = datePicker.year
                    val hariDeparture = datePicker.dayOfMonth
                    val tanggalDeparture = "$tahunDeparture-${month + 1}-$hariDeparture"

                    berandaViewModel.saveDepartureDate(tanggalDeparture)
                    binding.etDate.setText(tanggalDeparture)
                    findNavController().navigate(R.id.action_hasilPencarianReturnFragment_to_hasilPencarianPFragment)
                }
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.darkblue_05))
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.darkblue_05))
            }
        }
    }

    private fun dateToolbarReturn(dateReturn: String?) {
        if (dateReturn != null) {
            binding.etDateReturn.setText(dateReturn)
            binding.etDateReturn.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(
                    requireContext(), R.style.DateDialogTheme,
                    { _, year, month, dayOfMonth ->

                        val tanggalKembali = "$year-${month + 1}-$dayOfMonth"
                        binding.etDateReturn.setText(tanggalKembali)
                    },
                    year, month, day,
                )
                datePickerDialog.show()
                datePickerDialog.setOnDateSetListener { datePicker, _, _, _ ->
                    val month = datePicker.month
                    val tahunDeparture = datePicker.year
                    val hariDeparture = datePicker.dayOfMonth
                    val tanggalReturn = "$tahunDeparture-${month + 1}-$hariDeparture"
                    binding.etDateReturn.setText(tanggalReturn)
                    berandaViewModel.saveDatePref(tanggalReturn)
                    val fragId = findNavController().currentDestination?.id
                    findNavController().popBackStack(fragId!!,true)
                    findNavController().navigate(fragId)
                }
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.darkblue_05))
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.darkblue_05))
            }
        }
    }

    private fun returnOnly(
        cityFrom: String?,
        cityTo: String?,
        seatClass: String?,
       dateReturn: String?
    ) {

        val cityToReturn = cityFrom
        val cityFromReturn = cityTo
        berandaViewModel.ticketsBeranda(cityFromReturn!!, cityToReturn!!, seatClass!!, dateReturn!!)
        berandaViewModel.getTicketsBeranda.observe(viewLifecycleOwner) {
            binding.rvDeparture.apply {
//                binding.emptyResult.visibility = View.GONE
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                ticketAdapter = TicketAdapter(it) { itemTicket ->
                    val idReturn = itemTicket.id
                    val priceReturn = itemTicket.price
//                    val idDeparture = arguments?.getString("idDep")
//                    val hargaPergi = arguments?.getInt("pricePergi")
                    val bundle = Bundle()
                    berandaViewModel.saveIdReturn(idReturn)
//                    bundle.putString("idReturn", idReturn)
//                    bundle.putString("idDeparture",idDeparture)
//                    if (hargaPergi != null) {
//                        bundle.putInt("hargaPergi",hargaPergi)
//                    }
                    bundle.putInt("hargaPulang",priceReturn)
                    findNavController().navigate(R.id.action_hasilPencarianReturnFragment_to_detailPenerbanganPulangPergi,bundle)
                }
                adapter = ticketAdapter
                isNestedScrollingEnabled = false
            }
        }
        berandaViewModel.getErrorTicketBeranda.observe(viewLifecycleOwner){

            val jsonObject = JSONTokener(it).nextValue() as JSONObject
            val msg = jsonObject.getString("message")
            if (msg == "No tickets found") binding.emptyResult.visibility = View.VISIBLE
        }

    }
}