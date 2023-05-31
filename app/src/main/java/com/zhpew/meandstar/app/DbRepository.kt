package com.zhpew.meandstar.app

import android.content.Context
import com.zhpew.meandstar.db.StarRoomDatabase
import com.zhpew.meandstar.repository.CommemorationRepository

class DbRepository(context:Context) {

    // 数据库
    val DB by lazy {
        StarRoomDatabase.getDatabase(context)
    }

    // 数据库表
    val repository by lazy { CommemorationRepository(DB.CommemorationDao()) }
}