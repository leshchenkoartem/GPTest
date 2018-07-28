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
        @ColumnInfo(name = "time") var time: Long,
        @ColumnInfo(name = "accuracy") var accuracy: Float,
        @ColumnInfo(name = "provider") var provider: String
) {
    constructor() : this(0, 0.0, 0.0, 0, 0f, "")

    constructor(lat: Double, lon: Double, time: Long, accuracy: Float, provider: String)
            : this(null, lat, lon, time, accuracy, provider)

    constructor(lat: Double, lon: Double)
            : this(null, lat, lon, Date().time, 0f, "")
}
