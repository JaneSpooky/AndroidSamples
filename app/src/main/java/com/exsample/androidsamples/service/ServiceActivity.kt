package com.exsample.androidsamples.service

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import kotlinx.android.synthetic.main.service_activity.*



class ServiceActivity: BaseActivity() {

    private var service : MyService? = null

    private var serviceConnection = object: ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {}
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
           if (service != null && service is MyService.MyBinder)
               this@ServiceActivity.service = service.service
            updateLayout()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.service_activity)
        initialize()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService()
    }

    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        initClick()
        updateLayout()
    }

    private fun initClick() {
        closeImageView.setOnClickListener {
            finish()
        }
        startServiceButton.setOnClickListener {
            startService()
        }
        stopServiceButton.setOnClickListener {
            stopService()
        }
        showNotificationButton.setOnClickListener {
            showNotification()
        }
    }

    private fun updateLayout() {
        startServiceButton.isEnabled = service == null
        stopServiceButton.isEnabled = service != null
        showNotificationButton.isEnabled = service != null
    }

    private fun startService() {
        bindService(Intent(this, MyService::class.java), serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stopService() {
        unbindService(serviceConnection)
        service = null
        updateLayout()
    }

    private fun showNotification() {
        service?.showNotification()
    }

    companion object {
        fun start(activity: Activity) = activity.startActivity(Intent(activity, ServiceActivity::class.java))
    }
}