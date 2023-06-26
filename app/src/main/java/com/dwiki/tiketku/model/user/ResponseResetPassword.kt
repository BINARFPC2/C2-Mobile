package com.dwiki.tiketku.model.user

import com.google.gson.annotations.SerializedName

data class ResponseResetPassword(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
