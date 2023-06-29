package com.dwiki.tiketku.network

import com.dwiki.tiketku.model.destinasifavorit.DataItem
import com.dwiki.tiketku.model.destinasifavorit.ResponseDestinasiFavorit
import com.dwiki.tiketku.model.history.ResponseRiwayat
import com.dwiki.tiketku.model.notifikasi.ResponseNotifikasi
import com.dwiki.tiketku.model.payment.ResponsePayment
import com.dwiki.tiketku.model.penumpang.PenumpangRequest
import com.dwiki.tiketku.model.penumpang.ResponsePenumpang
import com.dwiki.tiketku.model.ticket.DataItemTicket
import com.dwiki.tiketku.model.ticket.ResponseDetailTicket
import com.dwiki.tiketku.model.ticket.ResponseTicket
import com.dwiki.tiketku.model.ticket.ResponseUpdateTicket
import com.dwiki.tiketku.model.user.ResponseProfile
import com.dwiki.tiketku.model.user.ResponseResetPassword
import com.dwiki.tiketku.model.user.ResponseUserLogin
import com.dwiki.tiketku.model.user.ResponseUserRegister
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
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

    @GET("users/{id}")
    fun getUserItem(
        @Path("id") id:String
    ):Call<ResponseUserRegister>

   @GET("destfavorite")
   fun getDestinasiFavorit(): Call<ResponseDestinasiFavorit>

   @GET("tickets")
   fun getTickets():Call<ResponseTicket>

   @GET("tickets")
   fun getTicketsBerandaP(
       @Query("city_from") city_from:String,
       @Query("city_to") city_to:String,
       @Query("type_seat") type_seat:String,
       @Query("dateDeparture") dateDeparture: String,
   ):Call<ResponseTicket>

    @GET("tickets")
    fun getTicketsBeranda(
        @Query("city_from") city_from:String,
        @Query("city_to") city_to:String,
        @Query("type_seat") type_seat:String,
        @Query("dateDeparture") dateDeparture: String,
    ):Call<ResponseTicket>



   @FormUrlEncoded
   @PUT("tickets/{id}")
   fun updateTicket(
       @Path("id") id:String,
       @Field("dateDeparture") dateDeparture:String,
       @Field("dateReturn") dateReturn:String,
       @Field("total_passenger") total_passenger:Int,
   ):Call<ResponseUpdateTicket>

   @FormUrlEncoded
   @PUT("tickets/{id}")
   fun updateTicketDeparture(
       @Path("id") id:String,
       @Field("dateDeparture") dateDeparture:String,
       @Field("total_passenger") total_passenger:Int
   ):Call<ResponseUpdateTicket>

   @GET("tickets/{id}")
    fun getTicketById(
       @Path("id") id:String
   ):Call<ResponseDetailTicket>

    @POST("checkout")
    fun postCheckoutPenumpang(
        @Header("Authorization") token:String,
        @Body requestPenumpang:PenumpangRequest
    ):Call<ResponsePenumpang>

    @FormUrlEncoded
    @PUT("reset-password")
    suspend fun resetPassword(
        @Field("password") password: String,
        @Field("confirmPassword") confirmPassword:String
    ):Response<ResponseResetPassword>

    @FormUrlEncoded
    @POST("payment")
    fun postPayment(
        @Header("Authorization") token:String,
        @Field("cardNumber") cardNumber:String,
        @Field("cardHolderName") cardHolderName:String,
        @Field("cvc") cvc:Int,
        @Field("expiration") expiration:String
    ):Call<ResponsePayment>

    @GET("whoami")
    fun checkProfile(
        @Header("Authorization") token:String
    ):Call<ResponseProfile>

    @GET("transaction")
    fun getHistoryTransaction(
        @Header("Authorization") token:String
    ):Call<ResponseRiwayat>

    @GET("notif")
    fun getNotifikasi(
        @Header("Authorization") token:String
    ):Call<ResponseNotifikasi>


}