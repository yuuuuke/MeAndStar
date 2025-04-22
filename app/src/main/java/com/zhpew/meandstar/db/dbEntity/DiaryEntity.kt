package com.zhpew.meandstar.db.dbEntity

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("table_diary")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = -1,
    var editTime: Long,
    var title: String,
    var textContent: String,
    var diaryItem: ArrayList<DiaryItemEntity>,
    var bg: String?,
)

data class DiaryItemEntity(
    // 0 图片，1 文字
    var type: Int = 0,
    var text: String?,
    var images: ArrayList<String>?,
    var index: Int
)