package com.zhpew.meandstar.db.dbEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("table_commemoration")
data class CommemorationDayEntity(var content: String, // 内容
                                  var date: Long,// 日期时间戳
                                  var showInDesktop:Boolean = false // 展示在桌面小组件上面
                                ) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}