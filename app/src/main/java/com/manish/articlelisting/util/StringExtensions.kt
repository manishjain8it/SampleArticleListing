package com.manish.articlelisting.util

import android.util.Log
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.ln
import kotlin.math.pow

fun withSuffix(count: Long): String? {
    if (count < 1000) return "" + count
    val exp = (ln(count.toDouble()) / ln(1000.0)).toInt()
    val format = DecimalFormat("0.#")
    val value = format.format(count / 1000.0.pow(exp.toDouble()))
    return String.format("%s%c", value, "KMBTPE"[exp - 1])
}

fun convertTimeToText(dataDate: String?): String? {
    var convertTime: String? = null
    val suffix = ""

    if (dataDate.isNullOrEmpty()) {
        convertTime = ""
        return convertTime
    }

    try {
        val pattern = "yyyy-MM-dd'T'HH:mm:ss"
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        val pasTime: Date? = dateFormat.parse(dataDate)
        val nowTime = Date()
        val dateDiff: Long = nowTime.time - pasTime!!.time
        val second: Long = TimeUnit.MILLISECONDS.toSeconds(dateDiff)
        val minute: Long = TimeUnit.MILLISECONDS.toMinutes(dateDiff)
        val hour: Long = TimeUnit.MILLISECONDS.toHours(dateDiff)
        val day: Long = TimeUnit.MILLISECONDS.toDays(dateDiff)
        if (second < 60) {
            convertTime = "$second Sec $suffix"
        } else if (minute < 60) {
            convertTime = "$minute Min $suffix"
        } else if (hour < 24) {
            convertTime = "$hour Hr $suffix"
        } else if (day >= 7) {
            convertTime = if (day > 360) {
                (day / 360).toString() + " Years " + suffix
            } else if (day > 30) {
                (day / 30).toString() + " Months " + suffix
            } else {
                (day / 7).toString() + " Week " + suffix
            }
        } else if (day < 7) {
            convertTime = "$day Days $suffix"
        }
    } catch (e: ParseException) {
        convertTime = ""
        e.printStackTrace()
        Log.e("ConvTimeE", e.message!!)
    } catch (e: Exception) {
        convertTime = ""
        e.printStackTrace()
        Log.e("ConvTimeE", e.message!!)
    }
    return convertTime!!.trim()
}
