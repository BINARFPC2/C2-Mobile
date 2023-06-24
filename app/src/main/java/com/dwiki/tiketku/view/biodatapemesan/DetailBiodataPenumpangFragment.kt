package com.dwiki.tiketku.view.biodatapemesan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentBiodataPenumpangBinding
import com.dwiki.tiketku.databinding.FragmentDetailBiodataPenumpangBinding
import com.dwiki.tiketku.model.penumpang.PenumpangData
import com.dwiki.tiketku.viewmodel.TestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailBiodataPenumpangFragment : Fragment() {


    private lateinit var binding:FragmentDetailBiodataPenumpangBinding
    private val testViewModel:TestViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBiodataPenumpangBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val penumpang = arguments?.getString("penumpang")
        binding.tvToolbar.text = penumpang

        val dataList = testViewModel.getDataList()
        if (dataList.isNotEmpty()){
            Log.d("Data","${dataList[0]}")
        }




        binding.btnLogin.setOnClickListener {
//            val title = "Mr"
//            val name = "Nurul Ahsan"
//            val namaKeluarga = "KK"
//            val tanggalLahir = "22-01-2001"
//            val kewarganegaraan = "Indonesia"
//            val ktp = "123456789"
//            val negaraPenerbit = "Indo"

            val title = binding.edtTitle.text.toString()
            val name = binding.edtNamaLengkap.text.toString()
            val namaKeluarga = binding.edtNamaKeluargaPemesan.text.toString()
            val tanggalLahir = "22-01-2001"
            val kewarganegaraan = binding.edtKewarganegaraan.text.toString()
            val ktp = binding.edtKtp.text.toString()
            val negaraPenerbit = binding.edtNegaraPeenrbit.text.toString()

            val dataPenumpang = PenumpangData(ktp,tanggalLahir,namaKeluarga,kewarganegaraan,name,title)
            testViewModel.addData(dataPenumpang)
            findNavController().navigate(R.id.action_detailBiodataPenumpangFragment_to_biodataPenumpangFragment)

        }

    }

}