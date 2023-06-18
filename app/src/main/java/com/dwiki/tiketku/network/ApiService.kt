package com.dwiki.tiketku.network

import com.dwiki.tiketku.model.destinasifavorit.DataItem
import com.dwiki.tiketku.model.destinasifavorit.ResponseDestinasiFavorit
import com.dwiki.tiketku.model.ticket.DataItemTicket
import com.dwiki.tiketku.model.ticket.ResponseDetailTicket
import com.dwiki.tiketku.model.ticket.ResponseTicket
import com.dwiki.tiketku.model.ticket.ResponseUpdateTicket
import com.dwiki.tiketku.model.user.ResponseUserLogin
import com.dwiki.tiketku.model.user.ResponseUserRegister
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @FormUrlEncoded
    @POST("login")
   suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ResponseUserLogin>

   @FormUrlEncoded
   @POST("register")
   suspend fun registerUser(
       @Field("name") name:String,
       @Field("email") email:String,
       @Field("password") password: String,
       @Field("phone") phone:String
   ):Response<ResponseUserRegister>

   @GET("destfavorite")
   fun getDestinasiFavorit(): Call<ResponseDestinasiFavorit>

   @GET("tickets")
   fun getTickets():Call<ResponseTicket>

   @GET("tickets")
   fun getTicketsBeranda(
       @Query("city_from") city_from:String,
       @Query("city_to") city_to:String,
       @Query("type_seat") type_seat:String
   ):Call<ResponseTicket>

   @FormUrlEncoded
   @PUT("tickets/{id}")
   fun updateTicket(
       @Path("id") id:String,
       @Field("dateDeparture") dateDeparture:String,
       @Field("dateReturn") dateReturn:String,
       @Field("total_passenger") total_passenger:Int
   ):Call<ResponseUpdateTicket>

   @GET("tickets/{id}")
    fun getTicketById(
       @Path("id") id:String
   ):Call<ResponseDetailTicket>


}