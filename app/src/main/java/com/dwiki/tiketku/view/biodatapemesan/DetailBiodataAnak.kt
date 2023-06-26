package com.dwiki.tiketku.view.biodatapemesan

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentDetailBiodataAnakBinding
import com.dwiki.tiketku.viewmodel.TestViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailBiodataAnak : Fragment() {

    private lateinit var binding:FragmentDetailBiodataAnakBinding
    private val testViewModel: TestViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBiodataAnakBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val penumpang = arguments?.getString("penumpang")
        val indexPenumpang = arguments?.getInt("index")
        binding.tvToolbar.text = penumpang

        val dataList = testViewModel.getDataList()



    }

}