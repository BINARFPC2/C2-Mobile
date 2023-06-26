package com.dwiki.tiketku.view.akun

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dwiki.tiketku.databinding.FragmentUbahProfilBinding
import com.dwiki.tiketku.viewmodel.LoginViewModel
import com.dwiki.tiketku.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UbahProfilFragment : Fragment() {

    lateinit var binding: FragmentUbahProfilBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUbahProfilBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val token = loginViewModel.getTokenPreferences()
        userData(token!!)

    }

    private fun userData(token: String) {
        loginViewModel.getTokenPreferences()
        registerViewModel.getResponseRegister.observe(viewLifecycleOwner) { user ->
            binding.apply {
                etNamaProfil.setText(user.data.name)
                etEmailProfil.setText(user.data.email)
                etPasswordProfil.setText(user.data.password)
                etNomorTlpProfil.setText(user.data.phone)
            }
        }
    }
}