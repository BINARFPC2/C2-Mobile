package com.dwiki.tiketku.view.pilihdestinasi

import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.PencarianFromAdapter
import com.dwiki.tiketku.databinding.FragmentPilihDestnasiBinding
import com.dwiki.tiketku.model.DummyKelas
import com.dwiki.tiketku.model.ticket.DataItemTicket
import com.dwiki.tiketku.model.ticket.HasilKota
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PilihDestnasiFragment : Fragment() {

    private lateinit var binding:FragmentPilihDestnasiBinding
    private val berandaViewModel: BerandaViewModel by viewModels()
    private lateinit var adapterPencarian: PencarianFromAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPilihDestnasiBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fromCity = arguments?.getString("from")

        if (fromCity != null){
            berandaViewModel.cityFrom()
            berandaViewModel.getCityFrom.observe(viewLifecycleOwner){
                val cityList : java.util.ArrayList<String> = ArrayList()
                for (i in it.indices){
                    cityList.add(it[i].cityFrom)
                }
                val list = cityList.filter { element -> it.count { it.cityFrom == element  } > 1 }.distinct()
                val cityAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_pendaftaran, list)
                binding.rvHasilPencarian.adapter = cityAdapter
                binding.rvHasilPencarian.setOnItemClickListener{ parent, view, position, id ->
                    val selectedCity =  cityAdapter.getItem(position)
                    berandaViewModel.saveCityFrom(selectedCity!!)
                    findNavController().navigate(R.id.action_pilihDestnasiFragment_to_berandaFragment)
                }


                binding.etSearch.addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        cityAdapter.filter.filter(p0)
                        if (p0?.isEmpty()!!){
                            binding.rvHasilPencarian.visibility = View.GONE
                        } else {
                            binding.rvHasilPencarian.visibility = View.VISIBLE
                        }

                    }

                    override fun afterTextChanged(p0: Editable?) {}

                })
            }
        } else {
            berandaViewModel.cityTo()
            berandaViewModel.getCityTo.observe(viewLifecycleOwner){
                val cityList : java.util.ArrayList<String> = ArrayList()
                for (i in it.indices){
                    cityList.add(it[i].cityFrom)
                }
                val list = cityList.filter { element -> it.count { it.cityFrom == element  } > 1 }.distinct()
                val cityAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_pendaftaran, list)
                binding.rvHasilPencarian.adapter = cityAdapter
                binding.rvHasilPencarian.setOnItemClickListener{ parent, view, position, id ->
                    val selectedCity =  cityAdapter.getItem(position)
                    berandaViewModel.saveCityTo(selectedCity!!)
                    findNavController().navigate(R.id.action_pilihDestnasiFragment_to_berandaFragment)
                }

                binding.etSearch.addTextChangedListener(object : TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        cityAdapter.filter.filter(p0)
                        if (p0?.isEmpty()!!){
                            binding.rvHasilPencarian.visibility = View.GONE
                        } else {
                            binding.rvHasilPencarian.visibility = View.VISIBLE
                        }
                    }
                    override fun afterTextChanged(p0: Editable?) {}
                })
            }
        }

    }


}