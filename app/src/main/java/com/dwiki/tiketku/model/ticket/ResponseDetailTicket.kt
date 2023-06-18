package com.dwiki.tiketku.model.ticket

import com.google.gson.annotations.SerializedName

data class ResponseDetailTicket(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("booking_code")
	val bookingCode: String? = null,

	@field:SerializedName("airport_to")
	val airportTo: String? = null,

	@field:SerializedName("child_price")
	val childPrice: Any? = null,

	@field:SerializedName("adult_price")
	val adultPrice: Any? = null,

	@field:SerializedName("available")
	val available: Boolean? = null,

	@field:SerializedName("total_passenger")
	val totalPassenger: Any? = null,

	@field:SerializedName("dateReturn")
	val dateReturn: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("city_from")
	val cityFrom: String? = null,

	@field:SerializedName("dateLanding")
	val dateLanding: String? = null,

	@field:SerializedName("price")
	val price: Int? = null,

	@field:SerializedName("airlines")
	val airlines: String? = null,

	@field:SerializedName("airport_from")
	val airportFrom: String? = null,

	@field:SerializedName("information")
	val information: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("dateTakeoff")
	val dateTakeoff: String? = null,

	@field:SerializedName("type_seat")
	val typeSeat: String? = null,

	@field:SerializedName("city_to")
	val cityTo: String? = null,

	@field:SerializedName("dateDeparture")
	val dateDeparture: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
