package com.dwiki.tiketku.view.hasilpencarianempty

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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


@AndroidEntryPoint
class HasilPencarianFragment : Fragment() {


    private lateinit var binding:FragmentHasilPencarianBinding
    private val berandaViewModel:BerandaViewModel by viewModels()
    private lateinit var ticketAdapter:TicketAdapter


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
        val totalPassengers = dewasa + anak + bayi
        val seatClass = berandaViewModel.getNamaKelas()
        binding.tvToolbar.text = "$cityFrom < > $cityTo - $totalPassengers Penumpang - $seatClass"

        berandaViewModel.ticketsBeranda(cityFrom!!,cityTo!!, seatClass!!)
        berandaViewModel.getTicketsBeranda.observe(viewLifecycleOwner){
            binding.rvHasilPencarian.apply {
                layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                ticketAdapter = TicketAdapter(it){itemTicket ->
                    val id = itemTicket.id
                    val bundle = Bundle()
                    bundle.putString("id",id)
                    findNavController().navigate(R.id.action_hasilPencarianFragment_to_detailPenerbangan,bundle)
                }
                adapter  = ticketAdapter
            }
        }

    }

}