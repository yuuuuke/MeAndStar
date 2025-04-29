package com.zhpew.meandstar.db.dbDao

import androidx.room.*
import com.zhpew.meandstar.db.dbEntity.CommemorationDayEntity

@Dao
interface CommemorationDAO {
    @Query("SELECT * FROM table_commemoration order by date desc")
    suspend fun getAllData(): List<CommemorationDayEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: CommemorationDayEntity)

    @Query("DELETE FROM table_commemoration")
    suspend fun deleteAll()

    @Update
    suspend fun update(data: CommemorationDayEntity)

    @Delete
    suspend fun delete(data: CommemorationDayEntity)
}