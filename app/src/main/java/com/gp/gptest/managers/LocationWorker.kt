package com.gp.gptest.managers

import androidx.work.Worker
import com.gp.gptest.model.LocationHistory
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import java.util.*

/**
 *  Created by artem on 28.07.2018.
 */
class LocationWorker : Worker() {
    override fun doWork(): Result {
        runBlocking {
            async(UI) {
                val locationManager = MyLocationManager(applicationContext)
                locationManager.locationData.observeForever {
                    it?.let {
                        val locationHistory = LocationHistory(it.latitude,
                                it.longitude, Date().time, it.accuracy,it.provider)
                        DataBaseManager.getInstance(applicationContext).insert(locationHistory)
                    }
                }
            }.await()
        }

        return Result.SUCCESS
    }
}