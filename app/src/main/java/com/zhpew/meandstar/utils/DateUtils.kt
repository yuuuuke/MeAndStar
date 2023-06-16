package com.zhpew.meandstar.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 时间戳转字符串
 */
fun time2String(time: Long): String {
    if (time <= 0) {
        return ""
    }
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(Date(time))
}

fun string2Time(time: String): Long {
    return try {
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date? = df.parse(time)
        date?.time ?: 0
    } catch (e: java.lang.Exception) {
        0
    }
}