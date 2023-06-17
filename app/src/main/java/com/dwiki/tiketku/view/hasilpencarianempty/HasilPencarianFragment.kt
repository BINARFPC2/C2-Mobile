package com.dwiki.tiketku.view.hasilpencarianempty

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.PencarianFromAdapter
import com.dwiki.tiketku.databinding.FragmentHasilPencarianBinding
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HasilPencarianFragment : Fragment() {


    private lateinit var binding:FragmentHasilPencarianBinding


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
    }

}