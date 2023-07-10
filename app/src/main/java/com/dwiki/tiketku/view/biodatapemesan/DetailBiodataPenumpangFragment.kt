package com.dwiki.tiketku.view.biodatapemesan

import android.app.DatePickerDialog
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentBiodataPenumpangBinding
import com.dwiki.tiketku.databinding.FragmentDetailBiodataPenumpangBinding
import com.dwiki.tiketku.model.penumpang.PenumpangData
import com.dwiki.tiketku.viewmodel.TestViewModel
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DetailBiodataPenumpangFragment : Fragment() {


    private lateinit var binding:FragmentDetailBiodataPenumpangBinding
    private val testViewModel:TestViewModel by activityViewModels()
    private var titleAd:String? = null


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
        val indexPenumpang = arguments?.getInt("index")
        val hintTitle = resources.getStringArray(R.array.title)
        binding.tvToolbar.text = penumpang

        val dataList = testViewModel.getDataList()
        formTitle(hintTitle)

        if (dataList.isNotEmpty()){
            if (indexPenumpang in dataList.indices){
                Log.d("Data","${dataList.indices}")
                Log.d("Data","${dataList[0]}")
                val itemPenumpang = dataList[indexPenumpang!!]
                binding.edtTitle.setText(itemPenumpang.title)
                binding.edtNamaLengkap.setText(itemPenumpang.name)
                binding.edtNamaKeluargaPemesan.setText(itemPenumpang.familyName)
                binding.edtDateOfBirth.setText(itemPenumpang.dateofbirth)
                binding.edtKewarganegaraan.setText(itemPenumpang.citizenship)
                binding.edtKtp.setText(itemPenumpang.ktppaspor)
                editData(dataList, indexPenumpang)
            } else {
                sumbitData()
            }
        } else {
            sumbitData()
        }

        binding.btnSwitch.setOnCheckedChangeListener { p0, isChecked ->

            if (isChecked) {
                binding.tvNamaKeluargaPemesan.visibility = View.VISIBLE
                binding.edtNamaKeluargaPemesan.visibility = View.VISIBLE

            } else {
                binding.tvNamaKeluargaPemesan.visibility = View.GONE
                binding.edtNamaKeluargaPemesan.visibility = View.GONE

            }
        }

        binding.edtDateOfBirth.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),R.style.DateDialogTheme,
                { _, year, month, dayOfMonth ->
                   val tanggalLahir = "$year-${month+1}-$dayOfMonth"
                    binding.edtDateOfBirth.setText(tanggalLahir)
                },
                year, month, day,
            )
            datePickerDialog.show()
            datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.darkblue_05))
            datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.darkblue_05))
        }



    }

    private fun editData(
        dataList: List<PenumpangData>,
        indexPenumpang: Int?
    ) {
        binding.btnSimpan.setOnClickListener {
            Log.d("Detail Biodat", "On Clicckkk")
            val title = binding.edtTitle.text.toString()
            val name = binding.edtNamaLengkap.text.toString()
            val namaKeluarga = binding.edtNamaKeluargaPemesan.text.toString()
            val tanggalLahir = binding.edtDateOfBirth.text.toString()
            val kewarganegaraan = binding.edtKewarganegaraan.text.toString()
            val ktp = binding.edtKtp.text.toString()

            Log.d("detail biodata", "$title")

            dataList[indexPenumpang!!].title = title
            dataList[indexPenumpang].name = name
            dataList[indexPenumpang].familyName = namaKeluarga
            dataList[indexPenumpang].dateofbirth = tanggalLahir
            dataList[indexPenumpang].citizenship = kewarganegaraan
            dataList[indexPenumpang].ktppaspor = ktp

            findNavController().navigate(R.id.action_detailBiodataPenumpangFragment_to_biodataPenumpangFragment)
        }
    }

    private fun sumbitData() {
        binding.btnSimpan.setOnClickListener {

            Log.d("Detail Biodat", "On Clicckkk Submit Data")

//            val title = binding.edtTitle.text.toString()
            val name = binding.edtNamaLengkap.text.toString()
            val namaKeluarga = binding.edtNamaKeluargaPemesan.text.toString()
            val tanggalLahir = binding.edtDateOfBirth.text.toString()
            val kewarganegaraan = binding.edtKewarganegaraan.text.toString()
            val ktp = binding.edtKtp.text.toString()

            if (name.isNotEmpty()||namaKeluarga.isNotEmpty()||tanggalLahir.isNotEmpty()||kewarganegaraan.isNotEmpty()||ktp.isNotEmpty()){
                val dataPenumpang = PenumpangData(ktp, tanggalLahir, namaKeluarga, kewarganegaraan, name, titleAd!!)
                testViewModel.addData(dataPenumpang)
                findNavController().navigate(R.id.action_detailBiodataPenumpangFragment_to_biodataPenumpangFragment)
            } else {
                FancyToast.makeText(requireContext(),"Mohon isi lengkapi data terlebih dahulu",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show()
            }



        }
    }

    private fun formTitle(hintTitle: Array<String>) {
        binding.edtTitle.apply {
            val adapterTitle = ArrayAdapter(requireContext(), R.layout.dropdown_item, hintTitle)
            setAdapter(adapterTitle)
            hint = "Title"
            onItemClickListener = AdapterView.OnItemClickListener{_,_,position,_ ->
                titleAd = adapterTitle.getItem(position).toString()
            }
        }
    }

}