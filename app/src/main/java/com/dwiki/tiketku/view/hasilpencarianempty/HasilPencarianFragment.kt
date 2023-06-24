package com.dwiki.tiketku.view.hasilpencarianempty

import android.app.DatePickerDialog
import android.graphics.drawable.ColorDrawable
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
import com.dwiki.tiketku.adapter.PencarianFromAdapter
import com.dwiki.tiketku.adapter.TicketAdapter
import com.dwiki.tiketku.databinding.FragmentHasilPencarianBinding
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*


@AndroidEntryPoint
class HasilPencarianFragment : Fragment() {


    private lateinit var binding:FragmentHasilPencarianBinding
    private val berandaViewModel:BerandaViewModel by viewModels()
    private lateinit var ticketAdapter:TicketAdapter
    private var tanggalPergi:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHasilPencarianBinding.inflate(inflater, container, false)
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

        binding.tvToolbar.text = "$cityFrom > $cityTo - $totalPassengers Penumpang - $seatClass"
        departureOnly(cityFrom, cityTo, seatClass, dateDeparture)
//        pulangPergi(cityFrom, cityTo, totalPassengers, seatClass, dateRetun)

        dateToolbar(dateDeparture)
        binding.ivBackBeranda.setOnClickListener {
            findNavController().navigate(R.id.action_hasilPencarianFragment_to_berandaFragment)
        }
    }

    private fun dateToolbar(dateDeparture: String?) {
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

                        tanggalPergi = "$year-${month + 1}-$dayOfMonth"
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

//    private fun pulangPergi(
//        cityFrom: String?,
//        cityTo: String?,
//        totalPassengers: Int,
//        seatClass: String?,
//        dateRetun: String?
//    ) {
//        val isPulangPergi = berandaViewModel.getCheckSwitch()
//        if (isPulangPergi) {
//            binding.tvToolbar.text =
//                "$cityFrom < > $cityTo - $totalPassengers Penumpang - $seatClass"
//            binding.tvTiketKembali.visibility = View.VISIBLE
//            berandaViewModel.ticketBerandaP(cityTo!!, cityFrom!!, seatClass!!, dateRetun!!)
//            berandaViewModel.getTicketsBerandaP.observe(viewLifecycleOwner) {
//                binding.rvHasilPencarianP.apply {
//                    layoutManager =
//                        LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
//                    ticketAdapter = TicketAdapter(it) { itemTicket ->
//                        val id = itemTicket.id
//                        val bundle = Bundle()
//                        bundle.putString("id", id)
//                        findNavController().navigate(
//                            R.id.action_hasilPencarianFragment_to_detailPenerbangan,
//                            bundle
//                        )
//                    }
//                    adapter = ticketAdapter
//                    isNestedScrollingEnabled = false
//                }
//            }
//        } else {
//            binding.tvToolbar.text = "$cityFrom > $cityTo - $totalPassengers Penumpang - $seatClass"
//        }
//    }

    private fun departureOnly(
        cityFrom: String?,
        cityTo: String?,
        seatClass: String?,
        dateDeparture: String?
    ) {

        berandaViewModel.ticketsBeranda(cityFrom!!, cityTo!!, seatClass!!, dateDeparture!!)
        berandaViewModel.getTicketsBeranda.observe(viewLifecycleOwner) {
            binding.rvHasilPencarian.apply {
                binding.emptyResult.visibility = View.GONE
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                ticketAdapter = TicketAdapter(it) { itemTicket ->
                    val id = itemTicket.id
                    val bundle = Bundle()
                    bundle.putString("id", id)
                    findNavController().navigate(
                        R.id.action_hasilPencarianFragment_to_detailPenerbangan,
                        bundle
                    )
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