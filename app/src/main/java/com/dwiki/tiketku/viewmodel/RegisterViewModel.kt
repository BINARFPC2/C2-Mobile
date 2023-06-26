package com.dwiki.tiketku.viewmodel

import android.app.Service
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.dwiki.tiketku.model.user.ResponseResetPassword
import com.dwiki.tiketku.model.user.ResponseUserLogin
import com.dwiki.tiketku.model.user.ResponseUserRegister
import com.dwiki.tiketku.network.ApiService
import com.dwiki.tiketku.util.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(private val api:ApiService):ViewModel() {

   private val _getResponseRegister = MutableLiveData<ResponseUserRegister>()
    val getResponseRegister:LiveData<ResponseUserRegister> = _getResponseRegister


    private val _success = MutableLiveData<String>()
    val success:LiveData<String> = _success

    //set up error
    private val _error = MutableLiveData<String>()
    val error:LiveData<String> = _error

    //set up loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun registerResult(email: String,password: String,phone:String,name:String):LiveData<Resources<ResponseUserRegister>> = liveData {
        emit(Resources.loading(null))
        try {
            val response = api.registerUser(name, email, password, phone)
            if (response.isSuccessful){
                emit(Resources.success(response.body()))
                Log.d("Regis View model","success register ${response.message()}")
            }else{
                emit(Resources.error(response.errorBody()?.string(),null))
                Log.e("Regis View model","Error register : ${response.errorBody()?.string()}")
            }
        } catch (e:Exception){
            emit(Resources.error(e.message.toString(),null))
            Log.e("Regis View model","Error Exception login : ${e.cause.toString()}")
        }
    }


    fun resetPassword(password:String,confirmPassword:String):LiveData<Resources<ResponseResetPassword>> = liveData {
        emit(Resources.loading(null))
        try {
            val response = api.resetPassword(password,confirmPassword)
            if (response.isSuccessful){
                emit(Resources.success(response.body()))
                Log.d("Regis View model","success reset password ${response.message()}")
            } else {
                emit(Resources.error(response.errorBody()?.string(),null))
                Log.e("Regis View model","Error reset password : ${response.errorBody()?.string()}")
            }
        }catch (e:Exception){
            emit(Resources.error(e.message.toString(),null))
            Log.e("Regis View model","Error Exception reset Password : ${e.cause.toString()}")
        }
    }


//    fun registerUser(name:String,email:String,pass:String,phone:String){
//        _isLoading.value = true
//        api.registerUser(name,email,pass,phone).enqueue(object : Callback<ResponseUserRegister>{
//            override fun onResponse(
//                call: Call<ResponseUserRegister>,
//                response: Response<ResponseUserRegister>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful){
//                    _success.value = response.body()?.message
//                } else{
//                    _error.value = response.errorBody()?.string()
//                }
//            }
//            override fun onFailure(call: Call<ResponseUserRegister>, t: Throwable) {
//               Log.e("Regis View Model","${t.cause}")
//            }
//
//        })
//
//        }
//    }


}