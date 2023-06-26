package com.dwiki.tiketku.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwiki.tiketku.model.penumpang.PenumpangRequest
import com.dwiki.tiketku.model.penumpang.ResponsePenumpang
import com.dwiki.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class BiodataViewModel @Inject constructor(private val apiService: ApiService):ViewModel() {

    private val _getBiodataPenumpangResponse = MutableLiveData<ResponsePenumpang>()
    val getBiodataPenumpangResponse:LiveData<ResponsePenumpang> = _getBiodataPenumpangResponse

    fun biodataPenumpang(penumpang:PenumpangRequest,token:String){
        apiService.postCheckoutPenumpang("Bearer $token",penumpang).enqueue(object : Callback<ResponsePenumpang>{
            override fun onResponse(
                call: Call<ResponsePenumpang>,
                response: Response<ResponsePenumpang>
            ) {
                if (response.isSuccessful){
                    _getBiodataPenumpangResponse.value = response.body()
                } else {
                    Log.e("BiodataViewModel","${response.errorBody()?.string()}")
                }

            }

            override fun onFailure(call: Call<ResponsePenumpang>, t: Throwable) {
                Log.e("BiodataViewModel","${t.cause}")
            }

        })
    }
}