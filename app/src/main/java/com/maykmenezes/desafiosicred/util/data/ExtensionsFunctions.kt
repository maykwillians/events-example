package com.maykmenezes.desafiosicred.util.data

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun Long.timeStampToFormattedDataTime(): String =
    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(this)

fun String.nameIsInvalid() = !this.matches(Regex(validNameRegexPattern))

fun String.emailIsInvalid() = !this.matches(Regex(validEmailRegexPattern))

fun Double.doubleToFormattedCurrentMoney(): String {
    val brazilianFormat = DecimalFormat.getCurrencyInstance(Locale(
        Locale.getDefault().language,
        Locale.getDefault().country))
    return brazilianFormat.format(this)
}