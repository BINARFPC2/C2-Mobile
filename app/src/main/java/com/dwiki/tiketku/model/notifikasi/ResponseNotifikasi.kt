package com.dwiki.tiketku.model.notifikasi

import com.google.gson.annotations.SerializedName

data class ResponseNotifikasi(

	@field:SerializedName("data")
	val data: List<DataItemNotifikasi>,

	@field:SerializedName("message")
	val message: String
)

data class DataItemNotifikasi(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("read")
	val read: Boolean,

	@field:SerializedName("usersId")
	val usersId: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
