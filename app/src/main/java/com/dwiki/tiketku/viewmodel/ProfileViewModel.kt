package com.dwiki.tiketku.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwiki.tiketku.model.user.ResponseProfile
import com.dwiki.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject  constructor(private val apiService: ApiService): ViewModel() {

    private val _getProfileData = MutableLiveData<ResponseProfile>()
    val getProfileData:LiveData<ResponseProfile> = _getProfileData

    fun profileData(token:String){
        apiService.checkProfile("Bearer $token").enqueue(object : Callback<ResponseProfile>{
            override fun onResponse(
                call: Call<ResponseProfile>,
                response: Response<ResponseProfile>
            ) {
                if (response.isSuccessful){
                    _getProfileData.value = response.body()
                } else {
                    Log.e("ProfileViewModel","${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ResponseProfile>, t: Throwable) {
                Log.e("ProfileViewModel","${t.cause}")
            }
        })
    }

}