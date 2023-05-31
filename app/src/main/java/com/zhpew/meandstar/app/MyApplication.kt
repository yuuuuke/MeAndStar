package com.zhpew.meandstar.app

import android.app.Application
import com.zhpew.meandstar.db.StarRoomDatabase
import com.zhpew.meandstar.db.dbEntity.CommemorationDayEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MyApplication : Application() {
    companion object {
        lateinit var instance: Application
    }

    val repository:DbRepository = DbRepository(this)

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}