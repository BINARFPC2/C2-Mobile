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
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.biodata.AnakAdapter
import com.dwiki.tiketku.adapter.biodata.BayiAdapter
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
    private lateinit var anakAdapter: AnakAdapter
    private lateinit var bayiAdapter: BayiAdapter
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

        initDewasaAdapter()
        initAnakAdapter()
        initBayiAdapter()



        binding.btnLogin.setOnClickListener {
            val idTicket = berandaViewModel.getIdTicket()
            val dewasa = berandaViewModel.getPenumpangDewasa()
            val anak = berandaViewModel.getPenumpangAnak()
            val bayi = berandaViewModel.getPenumpangBayi()
            val total = dewasa + anak + bayi

            val dataList = testViewModel.getDataList()
            Log.d("Hasil Pencarian","$dataList")

            val penumpangData = PenumpangRequest(idTicket!!,dataList,total)

            val token = loginViewModel.getTokenPreferences()
            biodataViewModel.biodataPenumpang(penumpangData,token!!)
            biodataViewModel.getBiodataPenumpangResponse.observe(viewLifecycleOwner){
                if (it.status == "Success"){
                    Toast.makeText(requireContext(), "Berhasil Menambahkan data penumpang", Toast.LENGTH_SHORT).show()
                    berandaViewModel.deleteTicketId()
                }
            }
        }


    }

    private fun initBayiAdapter() {
        val jumlahBayi = berandaViewModel.getPenumpangBayi()
        val listBayi: ArrayList<PenumpangBayi> = ArrayList()
        for (i in 1..jumlahBayi) {
            listBayi.add(PenumpangBayi("Bayi $i"))
        }
        binding.rvBayi.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            bayiAdapter = BayiAdapter(listBayi)
            adapter = bayiAdapter
            bayiAdapter.setOnItemClickListener(object : BayiAdapter.OnItemClickListener{
                override fun onItemClick(position: Int,bayi: PenumpangBayi) {
                    val bundle = Bundle()
                    bundle.putString("penumpang", bayi.penumpang)
                    findNavController().navigate(R.id.action_biodataPenumpangFragment_to_detailBiodataBayi,bundle)
                    Toast.makeText(requireContext(), "posisi card ini $position", Toast.LENGTH_SHORT).show()
                }
            })
            setHasFixedSize(true)
        }
    }


    private fun initAnakAdapter() {
        val jumlahAnak = berandaViewModel.getPenumpangAnak()
        val listAnak: ArrayList<PenumpangAnak> = ArrayList()
        for (i in 1..jumlahAnak) {
            listAnak.add(PenumpangAnak("Anak $i"))
        }
        binding.rvAnak.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            anakAdapter = AnakAdapter(listAnak)
            adapter = anakAdapter
            anakAdapter.setOnItemClickListener(object : AnakAdapter.OnItemClickListener{
                override fun onItemClick(position: Int,anak: PenumpangAnak) {
                    val bundle = Bundle()
                    bundle.putString("penumpang", anak.penumpang)
                    findNavController().navigate(R.id.action_biodataPenumpangFragment_to_detailBiodataAnak,bundle)
                    Toast.makeText(requireContext(), "posisi card ini $position", Toast.LENGTH_SHORT).show()
                }

            })
            setHasFixedSize(true)
        }
    }


    private fun initDewasaAdapter() {
        val jumlahDewasa = berandaViewModel.getPenumpangDewasa()
        val listDewasa: ArrayList<PenumpangDewasa> = ArrayList()
        for (i in 1..jumlahDewasa) {
            listDewasa.add(PenumpangDewasa("Dewasa $i"))
        }
        binding.rvDewasa.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            dewasaAdapter = DewasaAdapter(listDewasa)
            adapter = dewasaAdapter
            dewasaAdapter.setOnItemClickListener(object :DewasaAdapter.OnItemClickListener{
                override fun onItemClick(position: Int,dewasa: PenumpangDewasa) {
                    Toast.makeText(requireContext(), "posisi card ini $position", Toast.LENGTH_SHORT).show()
                    val bundle = Bundle()
                    bundle.putString("penumpang", dewasa.penumpang)
                    findNavController().navigate(R.id.action_biodataPenumpangFragment_to_detailBiodataPenumpangFragment,bundle)
                }
            })
            setHasFixedSize(true)
        }
    }


}