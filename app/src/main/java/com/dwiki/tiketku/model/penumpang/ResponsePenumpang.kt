package com.dwiki.tiketku.model.penumpang

import com.google.gson.annotations.SerializedName

data class ResponsePenumpang(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
