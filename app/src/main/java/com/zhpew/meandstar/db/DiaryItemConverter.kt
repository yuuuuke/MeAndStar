package com.zhpew.meandstar.db

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.zhpew.meandstar.db.dbEntity.DiaryItemEntity

class DiaryItemConverter {
    @TypeConverter
    fun fromString(value: String): SnapshotStateList<DiaryItemEntity> {
        return Gson().fromJson<SnapshotStateList<DiaryItemEntity>>(
            value,
            ArrayList<String>().javaClass.genericSuperclass
        ).toMutableStateList()
    }

    @TypeConverter
    fun fromArrayList(list: SnapshotStateList<DiaryItemEntity>): String {
        return Gson().toJson(list.toList())
    }
}