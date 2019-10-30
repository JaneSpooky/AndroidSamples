package com.exsample.androidsamples.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.exsample.androidsamples.SampleApplication.Companion.applicationContext
import com.google.android.gms.maps.model.LatLng

object MyLocationManager : LocationListener {

    private const val DEFAULT_LATITUDE = 34.956249
    private const val DEFAULT_LONGITUDE = 135.758968

    private const val MIN_UPDATE_LOCATION_DURATION = 1000L
    private const val MIN_UPDATE_LOCATION_DISTANCE = 50F

    private val defaultLatLng: LatLng = LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)

    private val locationManager: LocationManager by lazy { applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    var currentLatLng: LatLng = defaultLatLng

    override fun onLocationChanged(location: Location?) {
        location?.also {
            currentLatLng = LatLng(it.latitude, it.longitude)
        }
    }
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    override fun onProviderEnabled(provider: String?) {}
    override fun onProviderDisabled(provider: String?) {}

    fun start() {
        if (isEnableLocation())
            startLocationUpdates()
    }

    fun stop() {
        locationManager.removeUpdates(this)
    }

    fun isEnableLocation(): Boolean {
        if (!isLocationPermissionGranted())
            return false
        if (isEnableGpsProvider())
            return true
        return false
    }

    fun isLocationPermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED


    fun isEnableGpsProvider(): Boolean =
        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

    fun getNearCurrentLatLng(): LatLng {
        val deltaLat = (if ((Math.random() * 10).toInt() % 2 == 0) -1 else 1) * Math.random() / 100
        val deltaLan = (if ((Math.random() * 10).toInt() % 2 == 0) -1 else 1) * Math.random() / 100
        return LatLng(currentLatLng.latitude + deltaLat, currentLatLng.longitude + deltaLan)
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_UPDATE_LOCATION_DURATION, MIN_UPDATE_LOCATION_DISTANCE, this)
        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.also {
            currentLatLng = LatLng(it.latitude, it.longitude)
        } ?: locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)?.also {
            currentLatLng = LatLng(it.latitude, it.longitude)
        }
    }
}