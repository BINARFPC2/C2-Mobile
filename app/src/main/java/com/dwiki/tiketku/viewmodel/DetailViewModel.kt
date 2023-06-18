package com.dwiki.tiketku.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dwiki.tiketku.model.ticket.DataItemTicket
import com.dwiki.tiketku.network.ApiService
import com.dwiki.tiketku.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val apiService: ApiService):ViewModel() {

    fun getDetailTicket(id:String):LiveData<Resources<DataItemTicket>> = liveData {
        emit(Resources.loading(null))
        try {
            val response = apiService.getTicketById(id)
            if (response.isSuccessful){
                emit(Resources.success(response.body()))
                Log.d(TAG,"success get Detail: ${response.message()}")
            }else{
                emit(Resources.error(response.errorBody()?.string(),null))
                Log.e(TAG,"Error get Detail : ${response.errorBody()?.string()}")
            }

        } catch (e:java.lang.Exception){
            emit(Resources.error(e.message.toString(),null))
            Log.e(TAG,"Error Exception Detail : ${e.cause.toString()}")
        }
    }
    companion object{
        const val TAG = "DetailViewModel"
    }
}