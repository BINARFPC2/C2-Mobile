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
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RiwayatFragment : Fragment() {

    private lateinit var binding: FragmentRiwayat2Binding

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
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_riwayatFragment_to_loginFragment)
        }
    }

}