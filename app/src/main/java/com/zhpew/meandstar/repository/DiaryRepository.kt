package com.zhpew.meandstar.repository

import com.zhpew.meandstar.db.dbDao.DiaryDAO
import com.zhpew.meandstar.db.dbEntity.DiaryEntity

class DiaryRepository(private val dao: DiaryDAO) {
    suspend fun updateData(data: DiaryEntity) {
        dao.insert(data)
    }

    suspend fun deleteData(data: DiaryEntity) {
        dao.delete(data)
    }

    suspend fun getAllData(): List<DiaryEntity> {
        return dao.getAllData()
    }

    suspend fun getDiaryById(id:Int):DiaryEntity{
        return dao.getDiaryById(id)
    }
}