package com.dwiki.tiketku.view.notifikasi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.NotifikasiAdapter
import com.dwiki.tiketku.databinding.FragmentNotifikasi2Binding
import com.dwiki.tiketku.viewmodel.LoginViewModel
import com.dwiki.tiketku.viewmodel.RiwayatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotifikasiFragment : Fragment() {

    private lateinit var binding:FragmentNotifikasi2Binding
    private val loginViewModel:LoginViewModel by viewModels()
    private lateinit var notifAdapter:NotifikasiAdapter
    private val riwayatViewModel:RiwayatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotifikasi2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStateLogin()

        val token = loginViewModel.getTokenPreferences()
        riwayatViewModel.notifikasi(token!!)
        riwayatViewModel.getNotifikasi.observe(viewLifecycleOwner){listNotifikasi ->
            binding.rvNotifikasi.apply {
                layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                notifAdapter = NotifikasiAdapter(listNotifikasi)
                adapter = notifAdapter
                addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL))
            }
        }
    }

    private fun setStateLogin() {
        loginViewModel.getLoginState().observe(viewLifecycleOwner) {
            if (it) {
                binding.layoutNotifikasi.visibility = View.VISIBLE
                binding.layoutNoLogin.visibility = View.GONE
            } else {
                binding.layoutNotifikasi.visibility = View.GONE
                binding.layoutNoLogin.visibility = View.VISIBLE
            }
        }
    }

}