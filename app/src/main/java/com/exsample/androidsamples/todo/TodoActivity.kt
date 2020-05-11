package com.exsample.androidsamples.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.exsample.androidsamples.base.BaseActivity

class TodoActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {

    }

    companion object {
        fun start(activity: Activity) = activity.startActivity(Intent(activity, TodoActivity::class.java))
    }
}