package com.dwiki.tiketku.view.biodatapemesan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.biodata.DewasaAdapter
import com.dwiki.tiketku.databinding.FragmentBiodataPenumpangBinding
import com.dwiki.tiketku.model.penumpang.*
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.dwiki.tiketku.viewmodel.BiodataViewModel
import com.dwiki.tiketku.viewmodel.LoginViewModel
import com.dwiki.tiketku.viewmodel.TestViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BiodataPenumpangFragment : Fragment() {

    private lateinit var binding:FragmentBiodataPenumpangBinding
    private val berandaViewModel:BerandaViewModel by viewModels()
    private  val testViewModel: TestViewModel by activityViewModels()
    private lateinit var dewasaAdapter:DewasaAdapter
    private val biodataViewModel:BiodataViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBiodataPenumpangBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        binding.btnLanjut.setOnClickListener {
            val idTicket = berandaViewModel.getIdTicket()
            val idDep = berandaViewModel.getIdDep()
            val idReturn = berandaViewModel.getIdReturn()
            val dewasa = berandaViewModel.getPenumpangDewasa()
            val anak = berandaViewModel.getPenumpangAnak()
            val bayi = berandaViewModel.getPenumpangBayi()
            val total = dewasa + anak + bayi
            val dataList = testViewModel.getDataList()
            Log.d("Hasil Pencarian", "$dataList")
            if (dataList.isEmpty()) {
                Toast.makeText(requireContext(), "Tolong isi biodata penumpang terlebih dahulu", Toast.LENGTH_SHORT).show()
            } else {
                val isRoundTrip = berandaViewModel.getCheckSwitch()
                if (isRoundTrip) twoWay(idDep, idReturn, dataList, total)  else  oneWay(idTicket, dataList, total)
            }
        }

    }

    private fun oneWay(
        idTicket: String?,
        dataList: List<PenumpangData>,
        total: Int
    ) {
        val penumpangData = PenumpangRequest(idTicket!!, null, dataList, total)
        val token = loginViewModel.getTokenPreferences()
        biodataViewModel.biodataPenumpang(penumpangData, token!!)
        biodataViewModel.getBiodataPenumpangResponse.observe(viewLifecycleOwner) {
            if (it.status == "Success") {
                Toast.makeText(
                    requireContext(),
                    "Berhasil Menambahkan data penumpang",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_biodataPenumpangFragment_to_checkout2)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Pastikan data yang anda masukan benar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun twoWay(
        idDep: String?,
        idReturn: String?,
        dataList: List<PenumpangData>,
        total: Int
    ) {
        val penumpangData = PenumpangRequest(idDep!!, idReturn!!, dataList, total)
        val token = loginViewModel.getTokenPreferences()
        biodataViewModel.biodataPenumpang(penumpangData, token!!)
        biodataViewModel.getBiodataPenumpangResponse.observe(viewLifecycleOwner) {
            if (it.status == "Success") {
                Toast.makeText(
                    requireContext(),
                    "Berhasil Menambahkan data penumpang",
                    Toast.LENGTH_SHORT
                ).show()
                findNavController().navigate(R.id.action_biodataPenumpangFragment_to_checkoutRoundTripFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Pastikan data yang anda masukan benar",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun initAdapter() {
        val dewasa = berandaViewModel.getPenumpangDewasa()
        val anak = berandaViewModel.getPenumpangAnak()
        val bayi = berandaViewModel.getPenumpangBayi()
        val total = dewasa + anak + bayi
        val jumlahDewasa = berandaViewModel.getPenumpangDewasa()

        val listDewasa: ArrayList<Penumpang> = ArrayList()
        //get Penumpang Dewasa
        for (i in 1..dewasa) {
            listDewasa.add(Penumpang("Dewasa $i"))
        }
        //get Penumpang Anak
        for (i in 1..anak){
            listDewasa.add(Penumpang("Anak $i"))
        }
        //get Penumpang Bayi
        for(i in 1..bayi){
            listDewasa.add(Penumpang("Bayi $i"))
        }



        binding.rvDewasa.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            dewasaAdapter = DewasaAdapter(listDewasa)
            adapter = dewasaAdapter
            dewasaAdapter.setOnItemClickListener(object :DewasaAdapter.OnItemClickListener{
                override fun onItemClick(position: Int) {
                    Toast.makeText(requireContext(), "posisi card ini $position", Toast.LENGTH_SHORT).show()
                    val bundle = Bundle()
                    val name = listDewasa[position].penumpang
                    bundle.putInt("index",position)
                    bundle.putString("penumpang",name)
                    findNavController().navigate(R.id.action_biodataPenumpangFragment_to_detailBiodataPenumpangFragment,bundle)
                }
            })
            isNestedScrollingEnabled = false
            setHasFixedSize(true)
        }
    }


}