package com.zhpew.meandstar.repository

import com.zhpew.meandstar.db.dbDao.CommemorationDAO
import com.zhpew.meandstar.db.dbEntity.CommemorationDayEntity
import kotlinx.coroutines.withContext

class CommemorationRepository(private val dao: CommemorationDAO) {

    suspend fun updateData(data: CommemorationDayEntity) {
        dao.insert(data)
    }

    suspend fun deleteData(data: CommemorationDayEntity) {
        dao.delete(data)
    }

    suspend fun getAllData(): List<CommemorationDayEntity> {
        return dao.getAllData()
    }
}