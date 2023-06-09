package com.dwiki.tiketku.view.beranda

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentBerandaBinding
import com.dwiki.tiketku.databinding.FragmentRiwayat2Binding
import java.util.Calendar


class BerandaFragment : Fragment() {


    private lateinit var binding: FragmentBerandaBinding
    private var kelas:String? = null
    private var tanggalKembali:String = "Tanggal"
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




        binding.tvPenumpang.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment_to_bottomSheetSetPenumpang)
        }

        binding.tvClass.setOnClickListener {
            findNavController().navigate(R.id.action_berandaFragment_to_bottomSheetKelasFragment)
        }

        getKelasPenerbangan()
        getTanggalKembali()
        datePickerReturn()
        datePickerDeparture()

    }

    private fun datePickerDeparture() {
        binding.tvDepartureDate.setOnClickListener {
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

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val bulan =nameMonth[month]
                    tanggalPergi = "$dayOfMonth $bulan $year"
                    binding.tvPilihTanggal.setText(tanggalPergi)
                },
                year, month, day,
            )
            datePickerDialog.show()
        }
    }


    private fun getTanggalKembali() {
        if (tanggalKembali == null) {
            binding.tvPilihTanggal.setText("Pilih Tanggal")
        } else {
            binding.tvPilihTanggal.setText(tanggalKembali)
            binding.tvPilihTanggal.setTextColor(resources.getColor(R.color.black))
        }
    }

    private fun datePickerReturn() {
        binding.tvPilihTanggal.setOnClickListener {
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

            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    val bulan =nameMonth[month]
                    tanggalKembali = "$dayOfMonth $bulan $year"
                    binding.tvPilihTanggal.setText(tanggalKembali)
                },
                year, month, day,
            )
            datePickerDialog.show()
        }
    }

    private fun getKelasPenerbangan(){
        kelas = arguments?.getString("kelas")
        if (kelas == null) {
            binding.tvClass.text = "Pilih Kelas"
            binding.tvClass.setTextColor(resources.getColor(R.color.darkblue_04))
        } else {
            binding.tvClass.text = kelas
            binding.tvClass.setTextColor(resources.getColor(R.color.black))
        }
    }

}