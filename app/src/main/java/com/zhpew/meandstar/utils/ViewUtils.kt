package com.zhpew.meandstar.utils

import android.content.Context
import android.view.WindowManager
import androidx.compose.runtime.Composable
import com.zhpew.meandstar.app.MyApplication

fun getScreenWidth(): Int {
    return (MyApplication.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        .width
}

fun getScreenHeight():Int{
    return (MyApplication.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        .height
}

