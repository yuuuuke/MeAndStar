package com.zhpew.meandstar.app

import android.app.Application

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
    }

    val repository:DbRepository = DbRepository(this)

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}