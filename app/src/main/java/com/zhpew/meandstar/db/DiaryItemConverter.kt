package com.zhpew.meandstar.db

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.zhpew.meandstar.db.dbEntity.DiaryItemEntity

class DiaryItemConverter {
    @TypeConverter
    fun fromString(value: String): ArrayList<DiaryItemEntity> {
        return Gson().fromJson<ArrayList<DiaryItemEntity>>(
            value,
            ArrayList<String>().javaClass.genericSuperclass
        )
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<DiaryItemEntity>): String {
        return Gson().toJson(list)
    }
}