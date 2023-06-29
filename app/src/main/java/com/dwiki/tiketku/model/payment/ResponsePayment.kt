package com.dwiki.tiketku.model.payment

import com.google.gson.annotations.SerializedName

data class ResponsePayment(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Data(

	@field:SerializedName("cvc")
	val cvc: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("cardHolderName")
	val cardHolderName: String? = null,

	@field:SerializedName("usersId")
	val usersId: String? = null,

	@field:SerializedName("expiration")
	val expiration: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("cardNumber")
	val cardNumber: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
