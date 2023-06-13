package com.dwiki.tiketku.viewmodel

import android.content.SharedPreferences
import android.util.JsonToken
import android.util.Log
import androidx.lifecycle.*
import com.dwiki.tiketku.model.datastore.PreferenceManager
import com.dwiki.tiketku.model.user.ResponseUserLogin
import com.dwiki.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.dwiki.tiketku.util.Resources
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val api:ApiService,
    private val prefManager: PreferenceManager,
    private val sharedPreferences: SharedPreferences
    ):ViewModel() {

    //set up loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userItemLogin = MutableLiveData<ResponseUserLogin>()
    val userItemLogin:LiveData<ResponseUserLogin> = _userItemLogin

    //set up error
    private val _error = MutableLiveData<String>()
    val error:LiveData<String> = _error


    //get user login
    fun loginResult(email: String,password: String):LiveData<Resources<ResponseUserLogin>> = liveData {
        emit(Resources.loading(null))
        try {
            val response = api.loginUser(email,password)
            if (response.isSuccessful){
                emit(Resources.success(response.body()))
                Log.d(TAG,"success login: ${response.message()}")
            }else{
                emit(Resources.error(response.errorBody()?.string(),null))
                Log.e(TAG,"Error login : ${response.errorBody()?.string()}")
            }
        } catch (e:Exception){
            emit(Resources.error(e.message.toString(),null))
            Log.e(TAG,"Error Exception login : ${e.cause.toString()}")
        }
    }


    //get login state
    fun getLoginState(): LiveData<Boolean> {
        return prefManager.getLoginState().asLiveData()
    }

    fun saveLoginState(state:Boolean){
        viewModelScope.launch {
            prefManager.saveLoginState(state)
        }
    }

    //save token
    fun saveTokenPreferences(value: String){
        val editor = sharedPreferences.edit()
        editor.putString("token",value)
        editor.apply()
    }

    //get token
    fun getTokenPreferences():String?{
        return sharedPreferences.getString("token","token kosong")
    }

    //logout
    fun logout(){
        viewModelScope.launch {
            prefManager.logout()
        }
    }

    fun logoutPref(){
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object{
        private const val TAG = "LoginViewModel"
    }

}