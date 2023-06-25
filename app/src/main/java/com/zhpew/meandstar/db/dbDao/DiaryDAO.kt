package com.zhpew.meandstar.db.dbDao

import androidx.room.*
import com.zhpew.meandstar.db.dbEntity.DiaryEntity

@Dao
interface DiaryDAO{
    @Query("SELECT * FROM table_diary order by editTime desc")
    suspend fun getAllData(): List<DiaryEntity>

    @Query("SELECT * FROM table_diary where id = :id")
    suspend fun getDiaryById(id:Int): DiaryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: DiaryEntity)

    @Query("DELETE FROM table_diary")
    suspend fun deleteAll()

    @Update
    suspend fun update(data: DiaryEntity)

    @Delete
    suspend fun delete(data: DiaryEntity)
}