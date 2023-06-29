package com.dwiki.tiketku.model.history

import com.google.gson.annotations.SerializedName

data class ResponseRiwayat(

	@field:SerializedName("data")
	val data: List<DataItemRiwayat> ,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class DepartureTicket(

	@field:SerializedName("booking_code")
	val bookingCode: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("airport_to")
	val airportTo: String? = null,

	@field:SerializedName("available")
	val available: Boolean? = null,

	@field:SerializedName("dateEnd")
	val dateEnd: String? = null,

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

data class PassengersItem(

	@field:SerializedName("departureTicketsId")
	val departureTicketsId: String? = null,

	@field:SerializedName("dateofbirth")
	val dateofbirth: Any? = null,

	@field:SerializedName("citizenship")
	val citizenship: Any? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("checkoutsId")
	val checkoutsId: String? = null,

	@field:SerializedName("ktppaspor")
	val ktppaspor: Any? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("returnTicketsId")
	val returnTicketsId: String? = null,

	@field:SerializedName("expirationdatepass")
	val expirationdatepass: Any? = null,

	@field:SerializedName("phone")
	val phone: Any? = null,

	@field:SerializedName("familyName")
	val familyName: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("issuingcountry")
	val issuingcountry: Any? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class ReturnTicket(

	@field:SerializedName("booking_code")
	val bookingCode: String? = null,

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("airport_to")
	val airportTo: String? = null,

	@field:SerializedName("available")
	val available: Boolean? = null,

	@field:SerializedName("dateEnd")
	val dateEnd: String? = null,

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

data class DataItemRiwayat(

	@field:SerializedName("departureTicketsId")
	val departureTicketsId: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("returnTicketsId")
	val returnTicketsId: String? = null,

	@field:SerializedName("passengers")
	val passengers: List<PassengersItem?>? = null,

	@field:SerializedName("total_price")
	val totalPrice: Int? = null,

	@field:SerializedName("departureTicket")
	val departureTicket: DepartureTicket? = null,

	@field:SerializedName("usersId")
	val usersId: String? = null,

	@field:SerializedName("returnTicket")
	val returnTicket: ReturnTicket? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("total_passenger")
	val totalPassenger: Int? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
