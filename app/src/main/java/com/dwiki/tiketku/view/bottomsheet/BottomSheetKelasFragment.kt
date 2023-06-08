package com.dwiki.tiketku.view.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.SetKelasAdapter
import com.dwiki.tiketku.databinding.FragmentBottomSheetKelasBinding
import com.dwiki.tiketku.model.DummyKelas
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetKelasFragment : BottomSheetDialogFragment() {


    private lateinit var binding: FragmentBottomSheetKelasBinding
    private lateinit var kelasList:ArrayList<DummyKelas>
    private lateinit var kelasAdapter:SetKelasAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetKelasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClose.setOnClickListener {
            dismiss()
        }

        kelasList = ArrayList()
        kelasList.add(DummyKelas("Kelas 1","Rp.20000"))
        kelasList.add(DummyKelas("Kelas 2","Rp.20000"))
        kelasList.add(DummyKelas("Kelas 3","Rp.20000"))

        val selected = 0

        binding.rvKelas.apply {
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            kelasAdapter = SetKelasAdapter(kelasList,selected)
            adapter = kelasAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

    }

}