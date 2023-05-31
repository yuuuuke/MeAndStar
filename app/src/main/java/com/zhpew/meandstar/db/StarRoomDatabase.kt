package com.zhpew.meandstar.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zhpew.meandstar.db.dbDao.CommemorationDAO
import com.zhpew.meandstar.db.dbEntity.CommemorationDayEntity

@Database(entities = arrayOf(CommemorationDayEntity::class), version = 1, exportSchema = false)
public abstract class StarRoomDatabase : RoomDatabase() {

    abstract fun CommemorationDao(): CommemorationDAO

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