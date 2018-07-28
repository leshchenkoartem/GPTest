package com.gp.gptest.view.maps

import android.arch.lifecycle.LiveData
import android.location.Location
import android.support.v7.app.AppCompatActivity
import com.gp.gptest.model.LocationHistory

interface IMapsVM {
    fun getLastLocationFromDB(): LiveData<LocationHistory?>
    fun getLocationData(activity: AppCompatActivity): LiveData<Location>
    fun runWorker()
    fun stopWorker()
}
