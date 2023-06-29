package com.dwiki.tiketku.view.biodatapemesan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentBiodataPemesan2Binding
import com.dwiki.tiketku.databinding.FragmentBiodataPemesanBinding
import com.dwiki.tiketku.databinding.FragmentBiodataPenumpangBinding
import com.dwiki.tiketku.viewmodel.LoginViewModel
import com.dwiki.tiketku.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BiodataPemesan : Fragment() {

    private lateinit var binding:FragmentBiodataPemesan2Binding
    private val profileViewModel: ProfileViewModel by viewModels()
    private val loginVIewModel:LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBiodataPemesan2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = loginVIewModel.getTokenPreferences()
        profileViewModel.profileData(token!!)
        profileViewModel.getProfileData.observe(viewLifecycleOwner){
            binding.apply {
                tvNamaLengkapPemesan.setText(it.name)
                tvEmailPemesan.setText(it.email)
                tvNoTelfonPemesan.setText(it.phone)
            }
        }

        binding.btnSimpan.setOnClickListener {
            findNavController().navigate(R.id.action_biodataPemesan_to_biodataPenumpangFragment)
        }

        binding.switchh.setOnCheckedChangeListener { p0, isChecked ->
            if (isChecked) {
                binding.tvNamaKeluargaPemesan.visibility = View.VISIBLE
                binding.edtNamaKeluargaPemesan.visibility = View.VISIBLE

            } else {
                binding.tvNamaKeluargaPemesan.visibility = View.GONE
                binding.edtNamaKeluargaPemesan.visibility = View.GONE

            }
        }
    }


}