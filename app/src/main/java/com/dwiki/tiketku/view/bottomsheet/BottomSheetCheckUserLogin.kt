package com.dwiki.tiketku.view.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dwiki.tiketku.R
import com.dwiki.tiketku.databinding.FragmentBottomSheetCheckUserLoginBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetCheckUserLogin : BottomSheetDialogFragment() {

    private lateinit var binding:FragmentBottomSheetCheckUserLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetCheckUserLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

}