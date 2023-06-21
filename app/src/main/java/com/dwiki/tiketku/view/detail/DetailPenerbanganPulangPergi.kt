package com.dwiki.tiketku.view.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.SectionPagerAdapter
import com.dwiki.tiketku.databinding.FragmentDetailPenerbanganPulangPergiBinding
import com.dwiki.tiketku.util.Utill
import com.google.android.material.tabs.TabLayoutMediator

class DetailPenerbanganPulangPergi : Fragment() {

    private lateinit var binding:FragmentDetailPenerbanganPulangPergiBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailPenerbanganPulangPergiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idDeparture = arguments?.getString("idDeparture")
        val idReturn = arguments?.getString("idReturn")
        val hargaPergi = arguments?.getInt("hargaPergi")
        val hargaPulang = arguments?.getInt("hargaPulang")

        Log.d("DetailPenerbanganPulangPergi", "Harga: $hargaPergi")
        Log.d("DetailPenerbanganPulangPergi", "Harga: $hargaPulang")



        val sectionPagerAdapter = SectionPagerAdapter(activity as AppCompatActivity)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs,binding.viewPager){
            tab,position ->  tab.text = resources.getString(
                TAB_TITLES[position]
            )
        }.attach()

        val priceTotal = hargaPergi?.plus(hargaPulang!!)
        binding.txtHargaTotal.text = Utill.getPriceIdFormat(priceTotal!!)
    }
    companion object{
        private val TAB_TITLES = intArrayOf(
            R.string.str_pergi,
           R.string.str_pulang
        )
    }

}