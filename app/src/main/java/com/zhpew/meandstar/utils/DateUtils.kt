package com.zhpew.meandstar.utils

import androidx.compose.ui.res.stringResource
import com.zhpew.meandstar.R
import com.zhpew.meandstar.app.MyApplication
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar.*
import java.util.regex.Pattern

/**
 * 时间戳转字符串
 */
fun time2String(time: Long,pattern:String = "yyyy-MM-dd"): String {
    if (time <= 0) {
        return ""
    }
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
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

fun getWeekOfDate(time: Long):String{
    val cal = Calendar.getInstance();
    cal.time = Date(time);
    return when(cal.get(Calendar.DAY_OF_WEEK)){
        MONDAY -> MyApplication.instance.resources.getString(R.string.Monday)
        TUESDAY -> MyApplication.instance.resources.getString(R.string.Tuesday)
        WEDNESDAY -> MyApplication.instance.resources.getString(R.string.Wednesday)
        THURSDAY -> MyApplication.instance.resources.getString(R.string.Thursday)
        FRIDAY -> MyApplication.instance.resources.getString(R.string.Friday)
        SATURDAY -> MyApplication.instance.resources.getString(R.string.Saturday)
        else -> MyApplication.instance.resources.getString(R.string.Sunday)
    }
}