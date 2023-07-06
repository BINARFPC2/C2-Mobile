package com.dwiki.tiketku.view.akun

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentAkun2Binding
import com.dwiki.tiketku.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AkunFragment : Fragment() {

    private lateinit var binding:FragmentAkun2Binding
    private val loginViewModel:LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAkun2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.getLoginState().observe(viewLifecycleOwner){
            stateShowLogin(it)
        }

        binding.layoutKeluar.setOnClickListener {
            loginViewModel.logout()
            loginViewModel.logoutPref()
            findNavController().navigate(R.id.action_akunFragment_to_berandaFragment)
        }

        binding.layoutUbahProfil.setOnClickListener {
            findNavController().navigate(R.id.action_akunFragment_to_ubahProfilFragment)
        }

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_akunFragment_to_loginFragment)
        }
    }

    private fun stateShowLogin(it: Boolean) {
        if (it) {
            binding.layoutAkun.visibility = View.VISIBLE
            binding.layoutNoLogin.visibility = View.GONE
        } else {
            binding.layoutAkun.visibility = View.GONE
            binding.layoutNoLogin.visibility = View.VISIBLE
        }
    }

}