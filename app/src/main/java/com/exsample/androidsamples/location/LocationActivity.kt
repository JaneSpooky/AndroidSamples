package com.exsample.androidsamples.location

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import androidx.core.app.ActivityCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.location_activity.*

class LocationActivity: BaseActivity() {

    private var googleMap: GoogleMap? = null
    private var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.location_activity)
        initialize()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                checkEnableLocation()
            else
                showPermissionDeniedDialog()
        }
    }

    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        initClick()
        initMapView()
    }

    private fun initClick() {
        closeImageView.setOnClickListener {
            finish()
        }
        checkButton.setOnClickListener {
            checkPermission()
        }
        markerButton.setOnClickListener {
            updateMaker()
        }
    }

    private fun initMapView() {
        supportFragmentManager.beginTransaction()
            .add(R.id.mapView, SupportMapFragment.newInstance().apply {
                getMapAsync {
                    googleMap = it
                    initGoogleMap()
                }
            })
            .commit()
    }

    private fun initGoogleMap() {
        googleMap?.apply {
            uiSettings.apply {
                isMapToolbarEnabled = false
                isMyLocationButtonEnabled = false
                if (MyLocationManager.isLocationPermissionGranted())
                    isMyLocationEnabled = true
                moveCamera(CameraUpdateFactory.newLatLngZoom(MyLocationManager.currentLatLng, DEFAULT_ZOOM_LEVEL))
            }
        }
    }

    private fun checkPermission() {
        if (MyLocationManager.isLocationPermissionGranted()) {
            checkEnableLocation()
            return
        }
        requestLocationPermission()
    }

    private fun updateMaker() {
        marker?.remove()
        val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(ResourceUtil.getBitmap(this, R.drawable.ic_airplanemode_active_black_24dp))
        val latLng = MyLocationManager.getNearCurrentLatLng()
        val angle = (Math.random() * 360).toFloat()
        marker = googleMap?.addMarker(
            MarkerOptions()
                .position(latLng)
                .icon(bitmapDescriptor)
                .rotation(getDegree(MyLocationManager.currentLatLng, latLng))
        )
    }

    private fun getDegree(from: LatLng, to: LatLng): Float {
        val radian  = Math.atan2(to.longitude - from.longitude, to.latitude - from.latitude)
        return (radian * 180 / Math.PI).toFloat()
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_PERMISSION)
    }

    private fun checkEnableLocation() {
        if (MyLocationManager.isEnableGpsProvider())
            MyLocationManager.start()
        else
            requestGpsSetting()
    }

    private fun requestGpsSetting() {
        MaterialDialog(this).show {
            title(res = R.string.move_google_setting_dialog_title)
            message(res = R.string.move_google_setting_dialog_message)
            positiveButton(res = R.string.ok, click = {
                startActivity(Intent().apply {
                    action = Settings.ACTION_LOCATION_SOURCE_SETTINGS
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
            })
            negativeButton(res = R.string.cancel)
        }
    }

    private fun showPermissionDeniedDialog() {
        MaterialDialog(this).show {
            title(res = R.string.location_permission_denied_dialog_title)
            message(res = R.string.location_permission_denied_dialog_message)
            positiveButton(res = R.string.ok)
        }
    }

    companion object {
        private const val DEFAULT_ZOOM_LEVEL = 14F
        private const val REQUEST_CODE_PERMISSION = 1000
        fun start(activity: Activity) = activity.startActivity(Intent(activity, LocationActivity::class.java))
    }
}