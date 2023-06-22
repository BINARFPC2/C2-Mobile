package com.dwiki.tiketku.model.user

import com.google.gson.annotations.SerializedName

data class ResponseUserLogin(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("name")
	val Gaudname: String,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
