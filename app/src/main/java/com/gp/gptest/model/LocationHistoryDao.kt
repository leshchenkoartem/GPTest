package com.gp.gptest.model

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

/**
 *  Created by artem on 25.07.2018.
 */
@Dao
interface LocationHistoryDao {
    @Query("SELECT * from locationHistory")
    fun getAll(): List<LocationHistory>

    @Query("SELECT * from locationHistory order by time DESC")
    fun getAllByData(): DataSource.Factory<Int,LocationHistory>

    @Query("SELECT * from locationHistory order by time DESC limit 1")
    fun getLast(): LocationHistory?

    @Insert(onConflict = REPLACE)
    fun insert(locationHistory: LocationHistory)

    @Query("DELETE from locationHistory")
    fun deleteAll()

    @Delete
    fun delete(locationHistory: LocationHistory)
}