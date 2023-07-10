package com.dwiki.tiketku.model.riwayat

import com.google.gson.annotations.SerializedName

data class ResponseDetailRiwayat(

	@field:SerializedName("data")
	val data: List<DataItemDetailRiwayat>,

	@field:SerializedName("message")
	val message: String
)

data class DataItemDetailRiwayat(

	@field:SerializedName("departureTicketsId")
	val departureTicketsId: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("returnTicketsId")
	val returnTicketsId: String,

	@field:SerializedName("passengers")
	val passengers: List<PassengersItem>,

	@field:SerializedName("total_price")
	val totalPrice: Int,

	@field:SerializedName("departureTicket")
	val departureTicket: DepartureTicket,

	@field:SerializedName("usersId")
	val usersId: String,

	@field:SerializedName("returnTicket")
	val returnTicket: ReturnTicket,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("total_passenger")
	val totalPassenger: Int,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class ReturnTicket(

	@field:SerializedName("booking_code")
	val bookingCode: String,

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("airport_to")
	val airportTo: String,

	@field:SerializedName("available")
	val available: Boolean,

	@field:SerializedName("dateEnd")
	val dateEnd: String,

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

	@field:SerializedName("logo")
	val logo: String,

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

data class PassengersItem(

	@field:SerializedName("departureTicketsId")
	val departureTicketsId: String,

	@field:SerializedName("dateofbirth")
	val dateofbirth: String,

	@field:SerializedName("citizenship")
	val citizenship: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("checkoutsId")
	val checkoutsId: String,

	@field:SerializedName("ktppaspor")
	val ktppaspor: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("returnTicketsId")
	val returnTicketsId: String,

	@field:SerializedName("expirationdatepass")
	val expirationdatepass: Any,

	@field:SerializedName("phone")
	val phone: Any,

	@field:SerializedName("familyName")
	val familyName: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: Any,

	@field:SerializedName("issuingcountry")
	val issuingcountry: Any,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class DepartureTicket(

	@field:SerializedName("booking_code")
	val bookingCode: String,

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("airport_to")
	val airportTo: String,

	@field:SerializedName("available")
	val available: Boolean,

	@field:SerializedName("dateEnd")
	val dateEnd: String,

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

	@field:SerializedName("logo")
	val logo: String,

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
