package com.dwiki.tiketku.model.penumpang

import com.google.gson.annotations.SerializedName

data class PenumpangData (
    var ktppaspor: String,
    var dateofbirth: String,
    var familyName: String,
    var citizenship: String,
    var name: String,
    var title: String,
)

data class PenumpangRequest(
    var ticketsId:String,
    var passengers:List<PenumpangData>,
    var total_passenger:Int
)


data class Penumpang(
    var penumpang:String
)

