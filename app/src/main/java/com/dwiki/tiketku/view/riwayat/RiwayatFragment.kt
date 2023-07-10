package com.dwiki.tiketku.view.riwayat

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.RiwayatAdapter
import com.dwiki.tiketku.databinding.FragmentRiwayat2Binding
import com.dwiki.tiketku.viewmodel.LoginViewModel
import com.dwiki.tiketku.viewmodel.RiwayatViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RiwayatFragment : Fragment() {

    private lateinit var binding: FragmentRiwayat2Binding
    private val loginViewModel:LoginViewModel by viewModels()
    private lateinit var riwayatAdapter: RiwayatAdapter
    private val riwayatViewModel:RiwayatViewModel by viewModels()


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
        setStateLogin()
        val token  = loginViewModel.getTokenPreferences()
        setRecycleView(token)

    }

    private fun setStateLogin() {
        loginViewModel.getLoginState().observe(viewLifecycleOwner) {
            if (it) {
                binding.layoutRiwayat.visibility = View.VISIBLE
                binding.layoutNoLogin.visibility = View.GONE
            } else {
                binding.layoutRiwayat.visibility = View.GONE
                binding.layoutNoLogin.visibility = View.VISIBLE
            }
        }
    }

    private fun setRecycleView(token: String?) {
        riwayatViewModel.riwayatTransaction(token!!)
        riwayatViewModel.getRiwayatTransaction.observe(viewLifecycleOwner) { listRiwayat ->
            binding.itemRiwayatPesanan.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                riwayatAdapter = RiwayatAdapter(listRiwayat){
                    val id = it.id
                    val bundle = Bundle()
                    bundle.putString("idRiwayat",id)
                    findNavController().navigate(
                        R.id.action_riwayatFragment_to_detailRiwayatPesananFragment,bundle
                    )
                }
                adapter = riwayatAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun login() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_riwayatFragment_to_loginFragment)
        }
    }

}