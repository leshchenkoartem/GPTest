package com.gp.gptest.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 *  Created by artem on 25.07.2018.
 */
@Database(entities = arrayOf(LocationHistory::class), version = 1)
abstract class LocationHistoryDataBase : RoomDatabase() {
    abstract fun locationHistoryDao(): LocationHistoryDao

    companion object {
        private var instance: LocationHistoryDataBase? = null

        fun getInstance(context: Context): LocationHistoryDataBase {
            if (instance == null) {
                synchronized(LocationHistoryDataBase::class) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            LocationHistoryDataBase::class.java, "locationHistoryDataBase.db")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return instance!!
        }

        fun getInstance():LocationHistoryDataBase {
            return instance!!
        }

        fun destroyInstance() {
            instance?.close()
            instance = null
        }
    }
}