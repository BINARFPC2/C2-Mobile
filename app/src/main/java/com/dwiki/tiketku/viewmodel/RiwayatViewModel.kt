package com.dwiki.tiketku.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwiki.tiketku.model.history.DataItemRiwayat
import com.dwiki.tiketku.model.history.ResponseRiwayat
import com.dwiki.tiketku.model.notifikasi.DataItemNotifikasi
import com.dwiki.tiketku.model.notifikasi.ResponseNotifikasi
import com.dwiki.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class RiwayatViewModel @Inject constructor(private val apiService: ApiService):ViewModel() {

    private val _getRiwayatTransaction = MutableLiveData<List<DataItemRiwayat>>()
    val getRiwayatTransaction:LiveData<List<DataItemRiwayat>> = _getRiwayatTransaction

    private val _getNotifikasi = MutableLiveData<List<DataItemNotifikasi>>()
    val getNotifikasi:LiveData<List<DataItemNotifikasi>> = _getNotifikasi

    fun riwayatTransaction(token:String){
        apiService.getHistoryTransaction("Bearer $token").enqueue(object : Callback<ResponseRiwayat>{
            override fun onResponse(
                call: Call<ResponseRiwayat>,
                response: Response<ResponseRiwayat>
            ) {
                if (response.isSuccessful){
                    _getRiwayatTransaction.value = response.body()!!.data
                } else {
                    Log.e("RiwayatViewModel", " Error onResponse: ${response.errorBody()!!.string()}")
                }
            }

            override fun onFailure(call: Call<ResponseRiwayat>, t: Throwable) {
                Log.e("RiwayatViewModel", " Error onFailure: ${t.cause}")
            }

        })
    }

    fun notifikasi(token:String){
        apiService.getNotifikasi("Bearer $token").enqueue(object : Callback<ResponseNotifikasi>{
            override fun onResponse(
                call: Call<ResponseNotifikasi>,
                response: Response<ResponseNotifikasi>
            ) {
                if (response.isSuccessful){
                    _getNotifikasi.value = response.body()?.data
                } else {
                    Log.e("RiwayatViewModel", " Error Notifikasi onResponse: ${response.errorBody()!!.string()}")
                }
            }

            override fun onFailure(call: Call<ResponseNotifikasi>, t: Throwable) {
                Log.e("RiwayatViewModel", " Error Notifikasi onFailure: ${t.cause}")
            }

        })
    }

}