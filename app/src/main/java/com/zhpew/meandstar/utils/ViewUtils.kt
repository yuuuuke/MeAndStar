package com.zhpew.meandstar.utils

import android.content.Context
import android.view.WindowManager
import com.zhpew.meandstar.MyApplication

fun getScreenWidth(): Int {
    return (MyApplication.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        .width
}

fun getScreenHeight():Int{
    return (MyApplication.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        .height
}