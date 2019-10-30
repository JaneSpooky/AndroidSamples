package com.exsample.androidsamples

import android.app.Application
import android.content.Context
import timber.log.Timber

class SampleApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        SampleApplication.applicationContext = applicationContext
        initialize()
    }

    private fun initialize() {
        initTimber()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var applicationContext: Context
    }
}