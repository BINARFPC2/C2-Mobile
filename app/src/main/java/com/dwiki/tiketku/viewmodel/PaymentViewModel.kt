package com.dwiki.tiketku.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dwiki.tiketku.model.payment.ResponsePayment
import com.dwiki.tiketku.network.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val apiService: ApiService):ViewModel() {

    private val _getPayment = MutableLiveData<String>()
    val getPayment:LiveData<String> = _getPayment

    fun postPayment(token:String,cardNumber:String,cardHolderName:String,cvc:Int,expiration:String){
        apiService.postPayment(token,cardNumber,cardHolderName,cvc,expiration).enqueue(object : Callback<ResponsePayment>{
            override fun onResponse(
                call: Call<ResponsePayment>,
                response: Response<ResponsePayment>
            ) {
                if (response.isSuccessful){
                    _getPayment.value = response.body()?.status!!
                    Log.d("Payment View Model",response.body()?.status.toString())
                } else {
                    Log.d("Payment View Model", response.errorBody()?.string() ?: "Error")
                }
            }

            override fun onFailure(call: Call<ResponsePayment>, t: Throwable) {
                Log.d("Payment View Model",t.message.toString())
            }

        })

    }
}