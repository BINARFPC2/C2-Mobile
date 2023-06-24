package com.dwiki.tiketku.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.dwiki.tiketku.SingleLiveEvent
import com.dwiki.tiketku.model.KelasPenumpang
import com.dwiki.tiketku.model.Penumpang
import com.dwiki.tiketku.model.datastore.KelasManager
import com.dwiki.tiketku.model.datastore.PenumpangManager
import com.dwiki.tiketku.model.destinasifavorit.DataItem
import com.dwiki.tiketku.model.destinasifavorit.ResponseDestinasiFavorit
import com.dwiki.tiketku.model.penumpang.PenumpangData
import com.dwiki.tiketku.model.ticket.DataItemTicket
import com.dwiki.tiketku.model.ticket.ResponseTicket
import com.dwiki.tiketku.model.ticket.ResponseUpdateTicket
import com.dwiki.tiketku.network.ApiService
import com.dwiki.tiketku.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.List

@HiltViewModel
class BerandaViewModel @Inject constructor(
    private val kelasManager:KelasManager,
    private val apiService: ApiService,
    private val sharedPreferences: SharedPreferences
    ): ViewModel() {


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    private val _destinasiResult = MutableLiveData<ResponseDestinasiFavorit>()
    val destinasiResult:LiveData<ResponseDestinasiFavorit> = _destinasiResult

    private val _getCityFrom = MutableLiveData<List<DataItemTicket>>()
    val getCityFrom:LiveData<List<DataItemTicket>> = _getCityFrom

    private val _getCityTo = MutableLiveData<List<DataItemTicket>>()
    val getCityTo:LiveData<List<DataItemTicket>> = _getCityTo

    //biasa
    private val _getTicketsBeranda = MutableLiveData<List<DataItemTicket>>()
    val getTicketsBeranda:LiveData<List<DataItemTicket>> = _getTicketsBeranda

    //pulang pergi
    private val _getTicketsBerandaP = MutableLiveData<List<DataItemTicket>>()
    val getTicketsBerandaP:LiveData<List<DataItemTicket>> = _getTicketsBerandaP

    private val _getResponseUpdateTicket = MutableLiveData<String>()
    val getResponseUpdateTicket:LiveData<String> = _getResponseUpdateTicket

    private val _getResponseUpdateTicketDeparture = MutableLiveData<String>()
    val getResponseUpdateTicketDeparture:LiveData<String> = _getResponseUpdateTicketDeparture

    private val _getErrorTicketBeranda = MutableLiveData<String>()
    val getErrorTicketBeranda:LiveData<String> = _getErrorTicketBeranda

    //test untuk biodata penumpang
    var penumpangLiveData:MutableLiveData<ArrayList<PenumpangData>> = MutableLiveData()

    fun getPenumpangData(penumpang:PenumpangData){
        val list = penumpangLiveData.value
        list?.add(penumpang)
        penumpangLiveData.value = list!!
    }




    fun ticketsBeranda(cityFrom:String,cityTo:String,typeSeat:String,dateDeparture: String){
        apiService.getTicketsBeranda(cityFrom,cityTo,typeSeat,dateDeparture).enqueue(object :Callback<ResponseTicket>{
            override fun onResponse(
                call: Call<ResponseTicket>,
                response: Response<ResponseTicket>
            ) {
                if (response.isSuccessful){
                    _getTicketsBeranda.value = response.body()?.data
                    Log.d(TAG,"${response.body()?.data}")
                } else {
                    _getErrorTicketBeranda.value = response.errorBody()!!.string()
                    Log.d("error get ticket beranda", response.errorBody()!!.string())
                }

            }

            override fun onFailure(call: Call<ResponseTicket>, t: Throwable) {
                Log.d("failure get ticket beranda",t.cause.toString())
            }

        })
    }

    fun ticketBerandaP(cityFrom:String,cityTo:String,typeSeat:String,dateReturn:String){
        apiService.getTicketsBerandaP(cityFrom,cityTo,typeSeat,dateReturn).enqueue(object : Callback<ResponseTicket>{
            override fun onResponse(
                call: Call<ResponseTicket>,
                response: Response<ResponseTicket>
            ) {
                if (response.isSuccessful){
                    _getTicketsBerandaP.value = response.body()?.data
                } else {
                    Log.d("error get ticket beranda P", response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<ResponseTicket>, t: Throwable) {
                Log.d("failure get ticket berandaP",t.cause.toString())
            }

        })
    }

    fun updateTicket(id:String,dateDeparture:String,dateReturn:String,totalPassenger:Int){
        apiService.updateTicket(id,dateDeparture,dateReturn,totalPassenger).enqueue(object : Callback<ResponseUpdateTicket>{
            override fun onResponse(
                call: Call<ResponseUpdateTicket>,
                response: Response<ResponseUpdateTicket>
            ) {
                if (response.isSuccessful){
                    _getResponseUpdateTicket.value = response.body()?.message
                } else {
                    Log.d("error updater ticket beranda", response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<ResponseUpdateTicket>, t: Throwable) {
                Log.d("failure update ticket beranda",t.cause.toString())
            }

        })
    }

    fun updateTicketDeparture(id: String,dateDeparture: String,totalPassenger: Int){
        apiService.updateTicketDeparture(id,dateDeparture,totalPassenger).enqueue(object : Callback<ResponseUpdateTicket>{
            override fun onResponse(
                call: Call<ResponseUpdateTicket>,
                response: Response<ResponseUpdateTicket>
            ) {
                if (response.isSuccessful){
                    _getResponseUpdateTicketDeparture.value = response.body()?.message
                } else {
                    Log.d("error updater ticket deprture beranda", response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<ResponseUpdateTicket>, t: Throwable) {
                Log.d("failure update ticket Departure beranda",t.cause.toString())
            }

        })
    }

    fun getdestinasiFavResult(){
        _isLoading.value = true
        apiService.getDestinasiFavorit().enqueue(object : Callback<ResponseDestinasiFavorit>{
            override fun onResponse(
                call: Call<ResponseDestinasiFavorit>,
                response: Response<ResponseDestinasiFavorit>
            ) {
                if (response.isSuccessful){
                    _destinasiResult.value = response.body()
                } else {
                    Log.d("error get list", response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<ResponseDestinasiFavorit>, t: Throwable) {
                Log.e(TAG,"failure get list favorit: ${t.message}")
            }

        })
    }

    fun cityFrom(){
        apiService.getTickets().enqueue(object : Callback<ResponseTicket>{
            override fun onResponse(
                call: Call<ResponseTicket>,
                response: Response<ResponseTicket>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = true
                    _getCityFrom.value = response.body()?.data

                } else {
                    _isLoading.value = true
                    Log.d("error get city form", response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<ResponseTicket>, t: Throwable) {
                Log.e(TAG,"error get list Tikcet: ${t.message}")
            }

        })
    }

    fun cityTo(){
        apiService.getTickets().enqueue(object : Callback<ResponseTicket>{
            override fun onResponse(
                call: Call<ResponseTicket>,
                response: Response<ResponseTicket>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = true
                    _getCityTo.value = response.body()?.data

                } else {
                    _isLoading.value = true
                    Log.d("error get city To", response.errorBody()!!.string())
                }
            }

            override fun onFailure(call: Call<ResponseTicket>, t: Throwable) {
                Log.e(TAG,"error get list Tikcet: ${t.message}")
            }

        })
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

    fun saveIdDeparture(idDep:String){
        val editor = sharedPreferences.edit()
        editor.putString("idDep",idDep)
        editor.apply()
    }

    fun saveIdReturn(idReturn:String){
        val editor = sharedPreferences.edit()
        editor.putString("idReturn",idReturn)
        editor.apply()
    }

    fun saveCheckSwitch(isCheck: Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean("check",isCheck)
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

    fun saveCityFrom(city:String){
        val editor = sharedPreferences.edit()
        editor.putString("from",city)
        editor.apply()
    }

    fun saveCityTo(city:String){
        val editor = sharedPreferences.edit()
        editor.putString("to",city)
        editor.apply()
    }

    fun saveIdTicket(idTicket:String){
        val editor =  sharedPreferences.edit()
        editor.putString("idTicket",idTicket)
        editor.apply()
    }


    fun getCheckSwitch():Boolean{
        return sharedPreferences.getBoolean("check",false)
    }

    fun getPenumpangDewasa():Int{
        return sharedPreferences.getInt("dewasa",1)
    }

    fun getPenumpangAnak():Int{
        return sharedPreferences.getInt("anak",0)
    }

    fun getPenumpangBayi():Int{
        return sharedPreferences.getInt("bayi",0)
    }

    fun getCityFrom():String?{
        return sharedPreferences.getString("from","Jakarta")
    }

    fun getCityTo():String?{
        return sharedPreferences.getString("to","Jakarta")
    }

    fun getIdDep():String?{
        return sharedPreferences.getString("idDep","kosong")
    }

    fun getIdReturn():String?{
        return sharedPreferences.getString("idReturn","kosong")
    }

    fun getIdTicket():String?{
        return sharedPreferences.getString("idTicket","Ticket Id not found")
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
       val defaultTanggal = "$year-${month+1}-$day"
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
        val defaultTanggal = "$year-${month+1}-$day"
        return sharedPreferences.getString("departure",defaultTanggal)
    }

    fun getNamaKelas():String?{
        return sharedPreferences.getString("kelas","Economy")
    }

    fun getHargaKelas():Int{
        return sharedPreferences.getInt("harga",0)
    }

    fun getSelected():Boolean{
        return sharedPreferences.getBoolean("isSelected",false)
    }


    fun deletePref(){
        val editor = sharedPreferences.edit()
        editor.remove("check")
        editor.remove("dewasa")
        editor.remove("anak")
        editor.remove("bayi")
        editor.remove("date")
        editor.remove("kelas")
        editor.remove("harga")
        editor.remove("isSelected")
        editor.remove("departure")
        editor.apply()
    }

    fun deleteTicketId(){
        val editor = sharedPreferences.edit()
        editor.remove("idTicket")
        editor.apply()
    }




    companion object{
        private const val TAG = "DestinasiViewModel"
    }


}