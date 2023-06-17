package com.dwiki.tiketku.model.ticket

import com.google.gson.annotations.SerializedName

data class ResponseTicket(

	@field:SerializedName("data")
	val data: List<DataItemTicket>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataItemTicket(

	@field:SerializedName("booking_code")
	val bookingCode: String,

	@field:SerializedName("airport_to")
	val airportTo: String,

	@field:SerializedName("child_price")
	val childPrice: Any,

	@field:SerializedName("adult_price")
	val adultPrice: Any,

	@field:SerializedName("available")
	val available: Boolean,

	@field:SerializedName("dateEnd")
	val dateEnd: String,

	@field:SerializedName("total_passenger")
	val totalPassenger: Any,

	@field:SerializedName("dateReturn")
	val dateReturn: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("city_from")
	val cityFrom: String,

	@field:SerializedName("dateLanding")
	val dateLanding: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("airlines")
	val airlines: String,

	@field:SerializedName("airport_from")
	val airportFrom: String,

	@field:SerializedName("information")
	val information: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("dateTakeoff")
	val dateTakeoff: String,

	@field:SerializedName("type_seat")
	val typeSeat: String,

	@field:SerializedName("city_to")
	val cityTo: String,

	@field:SerializedName("dateDeparture")
	val dateDeparture: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
