package com.dwiki.tiketku.model.ticket

import com.dwiki.tiketku.util.Utill

data class DetailTicket(
  val airportFrom:String?,
  val airportTo:String?,
val cityFrom:String?,
val cityTo:String?,
val dateTakeoff:String?,
val dateDeparture:String?,
val airlines:String?,
val information:String?,
val dateLanding:String?,
val dateReturn:String?,
val price:Int?
)