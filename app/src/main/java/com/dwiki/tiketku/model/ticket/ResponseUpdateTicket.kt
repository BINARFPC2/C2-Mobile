package com.dwiki.tiketku.model.ticket

import com.google.gson.annotations.SerializedName

data class ResponseUpdateTicket(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
