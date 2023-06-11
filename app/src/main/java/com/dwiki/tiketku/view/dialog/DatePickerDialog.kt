package com.dwiki.tiketku.view.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.dwiki.tiketku.R
import com.dwiki.tiketku.viewmodel.BerandaViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class DatePickerDialog:DialogFragment() {

    private val berandaViewModel: BerandaViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val nameMonth = ArrayList<String>()
        nameMonth.add("Januari")
        nameMonth.add("Februari")
        nameMonth.add("Maret")
        nameMonth.add("April")
        nameMonth.add("Mei")
        nameMonth.add("Juni")
        nameMonth.add("Juli")
        nameMonth.add("Agustus")
        nameMonth.add("September")
        nameMonth.add("Oktober")
        nameMonth.add("November")
        nameMonth.add("Desember")

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

       return DatePickerDialog(
            requireContext(), R.style.DateDialogTheme,
            { _, year, month, dayOfMonth ->
                val bulan =nameMonth[month]
                val tanggalPergi = "$dayOfMonth $bulan $year"
                berandaViewModel.saveDatePref(tanggalPergi)
            },
            year, month, day,
        )
    }
}