package com.zhpew.meandstar.db

import android.content.Context
import androidx.room.*
import com.zhpew.meandstar.db.dbDao.CommemorationDAO
import com.zhpew.meandstar.db.dbDao.DiaryDAO
import com.zhpew.meandstar.db.dbEntity.CommemorationDayEntity
import com.zhpew.meandstar.db.dbEntity.DiaryEntity

@Database(entities = [CommemorationDayEntity::class, DiaryEntity::class], version = 1, exportSchema = false)
@TypeConverters(CustomConverter::class)
public abstract class StarRoomDatabase : RoomDatabase() {

    abstract fun CommemorationDao(): CommemorationDAO
    abstract fun DiaryDAO(): DiaryDAO

    companion object {

        @Volatile
        private var INSTANCE: StarRoomDatabase? = null

        fun getDatabase(context: Context): StarRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StarRoomDatabase::class.java,
                    "start_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}