package com.dwiki.tiketku.view.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.SectionPagerAdapter
import com.dwiki.tiketku.databinding.FragmentDetailPenerbanganPulangPergiBinding
import com.dwiki.tiketku.util.Utill
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.dwiki.tiketku.viewmodel.LoginViewModel
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailPenerbanganPulangPergi : Fragment() {

    private lateinit var binding:FragmentDetailPenerbanganPulangPergiBinding
    private val berandaViewModel:BerandaViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()


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
//        val idDeparture = arguments?.getString("idDeparture")
//        val idReturn = arguments?.getString("idReturn")
//        val hargaPergi = arguments?.getInt("hargaPergi")
        val hargaPergi = berandaViewModel.getPriceDep()
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
        val idrPrice = Utill.getPriceIdFormat(priceTotal!!)
        binding.txtHargaTotal.text = "$idrPrice/pax"

//        binding.btnPilih.setOnClickListener {
////            findNavController().navigate(R.id.action_detailPenerbanganPulangPergi_to_biodataPenumpangFragment)
//        }
        binding.btnPilih.setOnClickListener {
            loginViewModel.getLoginState().observe(viewLifecycleOwner){
                Log.d("DetailPenerbanganPulangPergi", "Login: $it")
                if (it) {

                    if (findNavController().currentDestination!!.id == R.id.detailPenerbanganPulangPergi){
                        findNavController().navigate(R.id.action_detailPenerbanganPulangPergi_to_biodataPemesan)
                    }
                } else {
                    val fragId = findNavController().currentDestination?.id
                    findNavController().popBackStack(fragId!!,true)
                    findNavController().navigate(R.id.bottomSheetCheckUserLogin)
                }

            }

        }
    }
    companion object{
        private val TAB_TITLES = intArrayOf(
            R.string.str_pergi,
           R.string.str_pulang
        )
    }

}