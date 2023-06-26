package com.dwiki.tiketku.view.riwayat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentRiwayat2Binding
import com.dwiki.tiketku.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RiwayatFragment : Fragment() {

    private lateinit var binding: FragmentRiwayat2Binding
    private val loginViewModel:LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentRiwayat2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login()

        loginViewModel.getLoginState().observe(viewLifecycleOwner){
            if (it){
                binding.layoutRiwayat.visibility = View.VISIBLE
                binding.layoutNoLogin.visibility = View.GONE
            } else{
                binding.layoutRiwayat.visibility = View.GONE
                binding.layoutNoLogin.visibility = View.VISIBLE
            }
        }

    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_riwayatFragment_to_loginFragment)
        }
    }

}