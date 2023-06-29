package com.dwiki.tiketku.view.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.SectionPagerAdapter
import com.dwiki.tiketku.databinding.FragmentCheckoutRoundTripBinding
import com.dwiki.tiketku.util.Utill
import com.dwiki.tiketku.view.detail.DetailPenerbanganPulangPergi
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.dwiki.tiketku.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CheckoutRoundTripFragment : Fragment() {

    private lateinit var binding:FragmentCheckoutRoundTripBinding
    private val berandaViewModel: BerandaViewModel by viewModels()
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutRoundTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sectionPagerAdapter = SectionPagerAdapter(activity as AppCompatActivity)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs,binding.viewPager){
                tab,position ->  tab.text = resources.getString(
                TAB_TITLES[position]
        )
        }.attach()

        val hargaDeparture = berandaViewModel.getPriceDep()
        val idReturn = berandaViewModel.getIdReturn()

        detailViewModel.detailTicket(idReturn!!)
        detailViewModel.getDetailTicket.observe(viewLifecycleOwner){
            val getDetail = it.data
            if (getDetail != null){
                val hargaReturn = getDetail.price
                val dewasa = berandaViewModel.getPenumpangDewasa()
                val anak = berandaViewModel.getPenumpangAnak()
                val bayi = berandaViewModel.getPenumpangBayi()
                val total = dewasa + anak + bayi

                val totalPriceDeparture = hargaDeparture*total
                val totalPriceReturn = hargaReturn?.times(total)
                val totalRoundTrip = totalPriceDeparture + totalPriceReturn!!
                val totalRoundTripIdr = Utill.getPriceIdFormat(totalRoundTrip)

                binding.txtTiketDewasa.text = "$total Penumpang"
                binding.txtHargaTiketDewasa.text = totalRoundTripIdr
                binding.txtTotalPembayaran.text = totalRoundTripIdr
                berandaViewModel.saveTotalTwo(totalRoundTrip)

            }
        }

        binding.btnCheckout.setOnClickListener {
            findNavController().navigate(R.id.action_checkoutRoundTripFragment_to_paymentRoundTripFragment)
        }

    }

    companion object{
        private val TAB_TITLES = intArrayOf(
            R.string.str_pergi,
            R.string.str_pulang
        )
    }
}