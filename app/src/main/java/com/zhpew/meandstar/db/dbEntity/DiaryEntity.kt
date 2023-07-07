package com.zhpew.meandstar.db.dbEntity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("table_diary")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = -1,
    var editTime: Long,
    var title:String,
    var textContent: String,
    var bg: String?,
    var images: ArrayList<String>?
)