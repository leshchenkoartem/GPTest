package com.gp.gptest.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 *  Created by artem on 25.07.2018.
 */
@Entity(tableName = "locationHistory")
data class LocationHistory(
        @PrimaryKey(autoGenerate = true) var id: Long?,
        @ColumnInfo(name = "lat") var lat: Double,
        @ColumnInfo(name = "lon") var lon: Double,
        @ColumnInfo(name = "time") var time: Long
){
    constructor():this(0, 0.0,0.0,0)
    constructor(lat: Double,lon: Double,time: Long):this(null,lat,lon,time)
    constructor(lat: Double,lon: Double):this(null,lat,lon, Date().time)
}