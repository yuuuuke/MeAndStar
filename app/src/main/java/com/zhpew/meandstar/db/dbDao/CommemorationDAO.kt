package com.zhpew.meandstar.db.dbDao

import androidx.room.*
import com.zhpew.meandstar.db.dbEntity.CommemorationDayEntity

//fun getAllCommemorationInfo():List<CommemorationDayBean>{
//    val list = ArrayList<CommemorationDayBean>()
//    list.add(CommemorationDayBean(null,"和星星相识",1663430400000))
//    list.add(CommemorationDayBean(null,"和星星相见",1664726400000))
//    list.add(CommemorationDayBean(null,"和星星相爱",1672416000000))
//    return list
//}

@Dao
interface CommemorationDAO {
    @Query("SELECT * FROM table_commemoration")
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