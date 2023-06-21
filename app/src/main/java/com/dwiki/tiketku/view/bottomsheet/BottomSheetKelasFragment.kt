package com.dwiki.tiketku.view.bottomsheet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dwiki.tiketku.R
import com.dwiki.tiketku.adapter.SetKelasAdapter
import com.dwiki.tiketku.databinding.FragmentBottomSheetKelasBinding
import com.dwiki.tiketku.model.DummyKelas
import com.dwiki.tiketku.model.KelasPenerbangan
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BottomSheetKelasFragment : BottomSheetDialogFragment(), SetKelasAdapter.OnItemClickListener {


    private lateinit var binding: FragmentBottomSheetKelasBinding
    private val berandaViewModel: BerandaViewModel by viewModels()
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
        kelasList.add(DummyKelas("Economy","Rp.20000"))
        kelasList.add(DummyKelas("Premium Economy","Rp.20000"))
        kelasList.add(DummyKelas("Business","Rp.20000"))
        kelasList.add(DummyKelas("First Class","Rp.20000"))

        binding.rvKelas.apply {
            layoutManager =LinearLayoutManager(requireContext())
            kelasAdapter = SetKelasAdapter(kelasList,this@BottomSheetKelasFragment)
            adapter = kelasAdapter
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }

    }

    override fun onItemClick(position: Int, adapter: SetKelasAdapter, v: View) {
        val rvKelas = binding.rvKelas
        val itemClicked: DummyKelas = kelasList[position]
        itemClicked.isSelected = !itemClicked.isSelected
        if (itemClicked.isSelected){
           rvKelas.getChildAt(rvKelas.indexOfChild(v)).findViewById<ConstraintLayout>(R.id.layout_set_kelas).setBackgroundResource(R.drawable.curved_bg_set_kelas)
           rvKelas.getChildAt(rvKelas.indexOfChild(v)).findViewById<ImageView>(R.id.succes_klik).visibility = View.VISIBLE
           rvKelas.getChildAt(rvKelas.indexOfChild(v)).findViewById<TextView>(R.id.tv_class).setTextColor(resources.getColor(R.color.white))
            rvKelas.getChildAt(rvKelas.indexOfChild(v)).findViewById<TextView>(R.id.tv_price).setTextColor(resources.getColor(R.color.white))

            //soon update using save args
            binding.btnSimpan.setOnClickListener {
                Log.d("Kelas", "Clicck")
                if (findNavController().currentDestination?.id == R.id.bottomSheetKelasFragment){
                    berandaViewModel.saveKelasPreferences(itemClicked.kelas,2000,itemClicked.isSelected)
                    findNavController().navigate(R.id.action_bottomSheetKelasFragment_to_berandaFragment)
                }

            }

            Log.d("Harga",itemClicked.harga)
            Log.d("Kelas", itemClicked.kelas)
            Log.d("Harga",itemClicked.harga)
        } else {
            rvKelas.getChildAt(rvKelas.indexOfChild(v)).findViewById<ConstraintLayout>(R.id.layout_set_kelas).setBackgroundResource(R.drawable.curve_set_kelas_stroke)
            rvKelas.getChildAt(rvKelas.indexOfChild(v)).findViewById<ImageView>(R.id.succes_klik).visibility = View.GONE
            rvKelas.getChildAt(rvKelas.indexOfChild(v)).findViewById<TextView>(R.id.tv_class).setTextColor(resources.getColor(R.color.black))
            rvKelas.getChildAt(rvKelas.indexOfChild(v)).findViewById<TextView>(R.id.tv_price).setTextColor(resources.getColor(R.color.darkblue_05))
        }
    }

}