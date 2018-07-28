package com.gp.gptest.view.maps

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.location.Location
import android.support.v7.app.AppCompatActivity
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.gp.gptest.managers.DataBaseManager
import com.gp.gptest.managers.LocationWorker
import com.gp.gptest.managers.MyLocationManager
import com.gp.gptest.model.LocationHistory
import java.util.concurrent.TimeUnit

/**
 *  Created by artem on 28.07.2018.
 */
class MapsVM:ViewModel(),IMapsVM {
    private val WORKER_TAG = "location"

    private var locationManager :MyLocationManager? = null
    private val locBaseManager = DataBaseManager.getInstance()

    override fun getLastLocationFromDB(): LiveData<LocationHistory?> {
        return locBaseManager.getLast()
    }

    override fun getLocationData(activity: AppCompatActivity): LiveData<Location> {
        if(locationManager == null) locationManager = MyLocationManager(activity)
        val location = MutableLiveData<Location>()
        val observ = Observer<Location>{
            location.postValue(it)
        }
        locationManager?.let {
            it.lastLocationData.observe(activity, observ)
            it.locationData.observe(activity, observ)
            it.getLastLocation()
            it.getLocation()
        }

        return location
    }

    override fun runWorker() {
        val workerRequest = PeriodicWorkRequest.Builder(LocationWorker::class.java,15,TimeUnit.MINUTES)
                .addTag(WORKER_TAG)
                .build()
        WorkManager.getInstance().enqueue(workerRequest)
    }

    override fun stopWorker() {
        WorkManager.getInstance().cancelAllWorkByTag(WORKER_TAG)
    }
}