package com.dwiki.tiketku.view.biodatapemesan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentDetailBiodataBayiBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailBiodataBayi : Fragment() {

    private lateinit var binding:FragmentDetailBiodataBayiBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBiodataBayiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val penumpang = arguments?.getString("penumpang")
        binding.tvToolbar.text = penumpang
    }


}