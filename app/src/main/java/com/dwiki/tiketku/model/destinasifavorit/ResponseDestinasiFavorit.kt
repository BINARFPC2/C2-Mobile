package com.dwiki.tiketku.model.destinasifavorit

import com.google.gson.annotations.SerializedName

data class ResponseDestinasiFavorit(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataItem(

	@field:SerializedName("continent")
	val continent: String,

	@field:SerializedName("date")
	val date: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("city_from")
	val cityFrom: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("airlines")
	val airlines: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("image_destinasi")
	val imageDestinasi: String,

	@field:SerializedName("info")
	val info: String,

	@field:SerializedName("city_to")
	val cityTo: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
