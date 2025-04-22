package com.zhpew.meandstar.db.dbEntity

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.zhpew.meandstar.db.DiaryItemConverter

@Entity("table_diary")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = -1,
    var editTime: Long,
    var title: String,
    var textContent: String,
    var bg: String?,
    var diaryItemJson: String = ""
){

    @Ignore
    var diaryItem: ArrayList<DiaryItemEntity>? = null
        set(value) {
            field = value
            field?.let { setChildren(it) }
        }

    fun setChildren(children: List<DiaryItemEntity>) {
        this.diaryItem = children as ArrayList<DiaryItemEntity>
        this.diaryItemJson = DiaryItemConverter().fromArrayList(children)
    }

    fun getChildren(): List<DiaryItemEntity> {
        return DiaryItemConverter().fromString(diaryItemJson)
    }
}

data class DiaryItemEntity(
    // 0 图片，1 文字
    var type: Int = 0,
    var text: String?,
    var images: ArrayList<String>?,
    var index: Int
)