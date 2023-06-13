package com.dwiki.tiketku.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.dwiki.tiketku.model.KelasPenumpang
import com.dwiki.tiketku.model.Penumpang
import com.dwiki.tiketku.model.datastore.KelasManager
import com.dwiki.tiketku.model.datastore.PenumpangManager
import com.dwiki.tiketku.model.destinasifavorit.DataItem
import com.dwiki.tiketku.model.destinasifavorit.ResponseDestinasiFavorit
import com.dwiki.tiketku.network.ApiService
import com.dwiki.tiketku.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class BerandaViewModel @Inject constructor(
    private val kelasManager:KelasManager,
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences
    ): ViewModel() {


   init {
       destinasiFavResult()
   }

    fun destinasiFavResult():LiveData<Resources<ResponseDestinasiFavorit>> = liveData {
        emit(Resources.loading(null))
        try {
            val response = apiService.getDestinasiFavorit()
            if (response.isSuccessful){
                emit(Resources.success(response.body()))
                Log.d(TAG,"success get list favorit: ${response.message()}")
            } else{
                emit(Resources.error(response.errorBody().toString(),null))
                Log.e(TAG,"error get list favorit: ${response.errorBody()?.string()}")
            }
        } catch (e:Exception){
            Log.e(TAG,"error get list favorit: ${e.message}")
            emit(Resources.error(e.cause.toString(),null))
        }
    }

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

    fun saveDepartureDate(date: String){
        val editor = sharedPreferences.edit()
        editor.putString("departure",date)
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

    fun getDepartureDate():String?{
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
        return sharedPreferences.getString("departure",defaultTanggal)
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


    companion object{
        private const val TAG = "DestinasiViewModel"
    }


}