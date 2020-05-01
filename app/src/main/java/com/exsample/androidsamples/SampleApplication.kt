package com.exsample.androidsamples

import android.app.Application
import android.content.Context
import android.graphics.Point
import android.util.DisplayMetrics
import android.view.WindowManager
import timber.log.Timber
import io.realm.Realm
import io.realm.RealmConfiguration

class SampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        SampleApplication.applicationContext = applicationContext
        initialize()
    }

    private fun initialize() {
        initTimber()
        initRealm()
        initDisplay()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initRealm() {
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build())
    }

    private fun initDisplay() {
        val size = Point()
        val metrics = DisplayMetrics()
        (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(size)
        (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(metrics)
        density = metrics.density
    }

    companion object {
        lateinit var applicationContext: Context
        var density = 1.0F
    }
}