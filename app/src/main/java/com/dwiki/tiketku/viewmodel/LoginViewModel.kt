package com.dwiki.tiketku.viewmodel

import android.util.JsonToken
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwiki.tiketku.model.user.ResponseUserLogin
import com.dwiki.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(private val api:ApiService):ViewModel() {

    //set up loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userItemLogin = MutableLiveData<ResponseUserLogin>()
    val userItemLogin:LiveData<ResponseUserLogin> = _userItemLogin

    //set up error
    private val _error = MutableLiveData<String>()
    val error:LiveData<String> = _error


    fun getUserLogin(email:String, password:String){
        _isLoading.value = true
        api.loginUser(email,password).enqueue(object : Callback<ResponseUserLogin> {
            override fun onResponse(
                call: Call<ResponseUserLogin>,
                response: Response<ResponseUserLogin>
            ) {
                if (response.isSuccessful){
                    _isLoading.value = false
                    _userItemLogin.value = response.body()
                }else{
                    _isLoading.value = false
                    _error.value = response.errorBody()?.string()
                }
            }

            override fun onFailure(call: Call<ResponseUserLogin>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message.toString())
            }

        })
    }
    companion object{
        private const val TAG = "LoginViewModel"
    }

}