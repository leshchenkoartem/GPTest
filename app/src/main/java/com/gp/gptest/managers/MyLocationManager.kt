package com.gp.gptest.managers

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.*

/**
 *  Created by artem on 28.07.2018.
 */
@SuppressLint("MissingPermission")
class MyLocationManager(context: Context) {
    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    val lastLocationData = MutableLiveData<Location>()
    val locationData = MutableLiveData<Location>()


    init {
        getLastLocation()
        getLocation()
    }

    fun getLastLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            val myLocation = it
            myLocation?.let {
                lastLocationData.value = it
            }
        }
    }

    fun getLocation() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
        fusedLocationClient.requestLocationUpdates(mLocationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                locationResult?.locations?.let {
                    it.map { locationData.value = it }
                }
            }
        }, Looper.myLooper())
    }
}