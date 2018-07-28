package com.gp.gptest.view.maps

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.gp.gptest.R
import com.gp.gptest.view.history.HistoryActivity
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions




@RuntimePermissions
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    lateinit var vm: IMapsVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment

        vm = ViewModelProviders.of(this).get(MapsVM::class.java)

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        vm.getLastLocationFromDB().observe(this, Observer {
            if (it != null) {
                val targetLocation = Location("")
                targetLocation.latitude = it.lat
                targetLocation.longitude = it.lon
                runToPosition(targetLocation)
            }
            MapsActivityPermissionsDispatcher.locationListenerWithCheck(this)

        })
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
    fun locationListener() {
        vm.getLocationData(this).observe(this, Observer {
            runToPosition(it)
        })
    }

    fun runToPosition(pos: Location?) {
        pos?.let {
            val pos = LatLng(pos.latitude,pos.longitude)
            mMap.clear()
            mMap.addMarker(MarkerOptions().position(pos).title("My position"))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 18f))
        }
    }

    override fun onStart() {
        super.onStart()
        vm.stopWorker()
    }

    override fun onStop() {
        super.onStop()
        vm.runWorker()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        MapsActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.map_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        when (item.getItemId()) {
            R.id.history_menu -> {
                startActivity(Intent(this,HistoryActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
