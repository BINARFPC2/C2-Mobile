package com.dwiki.tiketku.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.dwiki.tiketku.model.KelasPenumpang
import com.dwiki.tiketku.model.Penumpang
import com.dwiki.tiketku.model.datastore.KelasManager
import com.dwiki.tiketku.model.datastore.PenumpangManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class BerandaViewModel @Inject constructor(
    private val kelasManager:KelasManager,
    private val sharedPreferences: SharedPreferences
    ): ViewModel() {


    fun clearData(){
            viewModelScope.launch {
                kelasManager.clearData()
            }
        }

    fun savePenumpangPreferences(dewasa: Int,anak: Int,bayi: Int){
        val editor = sharedPreferences.edit()
        editor.putInt("dewasa",dewasa)
        editor.putInt("anak",anak)
        editor.putInt("bayi",bayi)
        editor.apply()
    }

    fun saveDatePref(date:String){
        val editor = sharedPreferences.edit()
        editor.putString("date", date)
        editor.apply()
    }

    fun saveKelasPreferences(nama:String,harga:Int,isSelected:Boolean){
        val editor = sharedPreferences.edit()
        editor.putString("kelas",nama)
        editor.putInt("harga",harga)
        editor.putBoolean("isSelected",isSelected)
        editor.apply()
    }

    fun getPenumpangDewasa():Int{
        return sharedPreferences.getInt("dewasa",2)
    }

    fun getPenumpangAnak():Int{
        return sharedPreferences.getInt("anak",0)
    }

    fun getPenumpangBayi():Int{
        return sharedPreferences.getInt("bayi",0)
    }

   fun getDatePref(): String? {
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

       val c = Calendar.getInstance()
       val year = c.get(Calendar.YEAR)
       val month = c.get(Calendar.MONTH)
       val day = c.get(Calendar.DAY_OF_MONTH)
       val bulan = nameMonth[month]
       val defaultTanggal = "$day $bulan $year"
       return sharedPreferences.getString("date",defaultTanggal)
   }

    fun getNamaKelas():String?{
        return sharedPreferences.getString("kelas","Kelas 1")
    }

    fun getHargaKelas():Int{
        return sharedPreferences.getInt("harga",0)
    }

    fun getSelected():Boolean{
        return sharedPreferences.getBoolean("isSelected",false)
    }


    fun deletePref(){
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }



}