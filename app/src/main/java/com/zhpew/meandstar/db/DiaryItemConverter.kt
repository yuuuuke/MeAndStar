package com.zhpew.meandstar.db

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.zhpew.meandstar.db.dbEntity.DiaryItemEntity

class DiaryItemConverter {
    @TypeConverter
    fun fromString(value: String): List<DiaryItemEntity> {
        return Gson().fromJson(
            value,
            Array<DiaryItemEntity>::class.java
        ).toList()
    }

    @TypeConverter
    fun fromArrayList(list: List<DiaryItemEntity>): String {
        return Gson().toJson(list)
    }
}