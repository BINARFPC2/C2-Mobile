package com.dwiki.tiketku.model.user

import com.google.gson.annotations.SerializedName

data class ResponseUserRegister(

	@field:SerializedName("data")
	val data: DataRegister,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class DataRegister(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("image_profile")
	val imageProfile: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("verified")
	val verified: Boolean,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
