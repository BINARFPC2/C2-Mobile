package com.dwiki.tiketku.view.beranda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentBerandaBinding
import com.dwiki.tiketku.databinding.FragmentRiwayat2Binding


class BerandaFragment : Fragment() {


    private lateinit var binding: FragmentBerandaBinding
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
    }

}