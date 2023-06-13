package com.dwiki.tiketku.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*

object Utill {

    fun getPriceIdFormat(price: Int): String {
//        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
//        return formatter.format(price.toInt())

       val id = Locale("id", "ID")
        val  decimalFormat = DecimalFormat("#,##0")
        val decimalFormatSymbols = DecimalFormatSymbols(id)
        decimalFormatSymbols.currencySymbol = "."
        decimalFormat.decimalFormatSymbols = decimalFormatSymbols

        return "IDR ${decimalFormat.format(price)}"

    }
}