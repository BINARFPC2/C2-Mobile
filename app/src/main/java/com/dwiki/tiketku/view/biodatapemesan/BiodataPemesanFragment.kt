package com.dwiki.tiketku.view.biodatapemesan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentBiodataPemesanBinding


class BiodataPemesanFragment : Fragment() {

    private lateinit var binding:FragmentBiodataPemesanBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBiodataPemesanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.switchh.setOnCheckedChangeListener{ p0, isChecked ->
            if (isChecked){
                binding.edtNamaKeluargaPemesan.visibility = View.VISIBLE
                binding.tvNamaKeluargaPemesan.visibility = View.VISIBLE
            } else {
                binding.edtNamaKeluargaPemesan.visibility = View.GONE
                binding.tvNamaKeluargaPemesan.visibility = View.GONE
            }
        }

        binding.btnSimpanBiodataPemesanan.setOnClickListener {
            findNavController().navigate(R.id.action_biodataPemesanFragment_to_biodataPenumpangFragment)
        }

    }



}