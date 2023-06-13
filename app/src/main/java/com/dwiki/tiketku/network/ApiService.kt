package com.dwiki.tiketku.network

import com.dwiki.tiketku.model.destinasifavorit.DataItem
import com.dwiki.tiketku.model.destinasifavorit.ResponseDestinasiFavorit
import com.dwiki.tiketku.model.user.ResponseUserLogin
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("login")
   suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ResponseUserLogin>

   @GET("destfavorite")
   suspend fun getDestinasiFavorit(): Response<ResponseDestinasiFavorit>

}