package com.dwiki.tiketku.view.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentBottomSheetKelasBinding
import com.dwiki.tiketku.databinding.FragmentBottomSheetSuksesBayarBinding
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.dwiki.tiketku.viewmodel.LoginViewModel
import com.dwiki.tiketku.viewmodel.PaymentViewModel
import com.dwiki.tiketku.viewmodel.TestViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetSuksesBayarFragment : BottomSheetDialogFragment() {

    private lateinit var binding:FragmentBottomSheetSuksesBayarBinding
    private val testViewModel:TestViewModel by viewModels()
    private val paymentViewModel:PaymentViewModel by viewModels()
    private val loginViewModel:LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetSuksesBayarBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = loginViewModel.getTokenPreferences()
        binding.btnCetak.setOnClickListener {

            paymentViewModel.eticket(token!!)
            paymentViewModel.getEticket.observe(viewLifecycleOwner){
                if (it.message == "E-Ticket data successfully obtained"){
                    FancyToast.makeText(requireContext(), "Silahkan Periksa Email Anda", FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                    testViewModel.removeData()
                    findNavController().navigate(R.id.action_bottomSheetSuksesBayarFragment_to_berandaFragment)
                }
            }
        }
    }

}