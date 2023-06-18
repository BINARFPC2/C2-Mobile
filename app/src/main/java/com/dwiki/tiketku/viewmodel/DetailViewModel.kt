package com.dwiki.tiketku.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dwiki.tiketku.model.ticket.DataItemTicket
import com.dwiki.tiketku.model.ticket.ResponseDetailTicket
import com.dwiki.tiketku.network.ApiService
import com.dwiki.tiketku.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val apiService: ApiService):ViewModel() {

    private val _getDetailTicket = MutableLiveData<ResponseDetailTicket>()
    val getDetailTicket:LiveData<ResponseDetailTicket> = _getDetailTicket

    fun detailTicket(id:String){
        apiService.getTicketById(id).enqueue(object : Callback<ResponseDetailTicket>{

            override fun onResponse(
                call: Call<ResponseDetailTicket>,
                response: Response<ResponseDetailTicket>
            ) {
                if (response.isSuccessful){
                    _getDetailTicket.value = response.body()
                } else{
                    Log.e(TAG,"Error get ticket by id")
                }
            }

            override fun onFailure(call: Call<ResponseDetailTicket>, t: Throwable) {
                Log.e(TAG,"Failure get ticket by id")
            }

        })
    }
    companion object{
        const val TAG = "DetailViewModel"
    }
}