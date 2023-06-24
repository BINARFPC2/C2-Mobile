package com.dwiki.tiketku.model.penumpang

import com.google.gson.annotations.SerializedName

data class PenumpangData (
    val ktppaspor: String,
    val dateofbirth: String,
    val familyName: String,
    val citizenship: String,
    val name: String,
    val title: String,
)

data class PenumpangRequest(
    var ticketId:String,
    var passenger:List<PenumpangData>,
    var totalPassenger:Int
)

data class PenumpangBiodata(
    var jumlah:Int,
    var jenis:String
)

data class PenumpangDewasa(
    var penumpang:String
)

data class PenumpangAnak(
    var penumpang:String
)

data class PenumpangBayi(
    var penumpang:String
)