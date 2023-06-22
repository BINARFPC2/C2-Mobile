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
import com.dwiki.tiketku.databinding.FragmentHasilPencarianPBinding
import com.dwiki.tiketku.model.ticket.DataItemTicket
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject
import org.json.JSONTokener
import java.util.*

@AndroidEntryPoint
class HasilPencarianPFragment : Fragment() {

    private lateinit var binding:FragmentHasilPencarianPBinding
    private val berandaViewModel: BerandaViewModel by viewModels()
    private lateinit var ticketAdapter:TicketAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHasilPencarianPBinding.inflate(layoutInflater, container, false)
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
        dateToolbarDeparture(dateDeparture)
        dateToolbarReturn(dateRetun)
        departureOnly(cityFrom, cityTo, seatClass, dateDeparture)
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
                    findNavController().navigate(R.id.hasilPencarianPFragment)
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
                }
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.darkblue_05))
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.darkblue_05))
            }
        }
    }

    private fun departureOnly(
        cityFrom: String?,
        cityTo: String?,
        seatClass: String?,
        dateDeparture: String?
    ) {

        berandaViewModel.ticketsBeranda(cityFrom!!, cityTo!!, seatClass!!, dateDeparture!!)
        berandaViewModel.getTicketsBeranda.observe(viewLifecycleOwner) {

            binding.testButton.setOnClickListener {view ->
                val listTicket:kotlin.collections.List<DataItemTicket> = it
                val filterTicket = filterTicketByPricingAsc(listTicket)
                filterTicket.forEach{
                    Log.d("Hasil Pencarian","Airline : ${it.airlines}, Price : ${it.price}")
                }
            }
            binding.rvDeparture.apply {
//                binding.emptyResult.visibility = View.GONE
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                ticketAdapter = TicketAdapter(it) { itemTicket ->
                    val id = itemTicket.id
                    val hargaPergi = itemTicket.price
                    val bundle = Bundle()
                    berandaViewModel.saveIdDeparture(id)
                    bundle.putString("idDep", id)
                    bundle.putInt("pricePergi",hargaPergi)
                    findNavController().navigate(R.id.action_hasilPencarianPFragment_to_hasilPencarianReturnFragment,bundle)
                }
                adapter = ticketAdapter
                isNestedScrollingEnabled = false
            }
        }
        berandaViewModel.getErrorTicketBeranda.observe(viewLifecycleOwner){

            val jsonObject = JSONTokener(it).nextValue() as JSONObject
            val msg = jsonObject.getString("message")
//            if (msg == "No tickets found") binding.emptyResult.visibility = View.VISIBLE
        }

    }

    fun filterTicketByPricingAsc(ticket:kotlin.collections.List<DataItemTicket>):kotlin.collections.List<DataItemTicket>{
        return ticket.sortedBy {
            it.price
        }
    }

}