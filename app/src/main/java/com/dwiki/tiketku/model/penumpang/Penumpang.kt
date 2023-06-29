package com.dwiki.tiketku.model.penumpang

data class PenumpangData (
    var ktppaspor: String,
    var dateofbirth: String,
    var familyName: String,
    var citizenship: String,
    var name: String,
    var title: String,
)

data class PenumpangRequest(
    var departureTicketsId:String,
    var returnTicketsId: String?,
    var passengers:List<PenumpangData>,
    var total_passenger:Int
)


data class Penumpang(
    var penumpang:String
)

