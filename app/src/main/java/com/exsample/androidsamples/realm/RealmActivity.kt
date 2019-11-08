package com.exsample.androidsamples.realm

import android.app.Activity
import android.content.Intent
import com.exsample.androidsamples.base.BaseActivity

class RealmActivity: BaseActivity() {

    companion object {
        fun start(activity: Activity) = activity.startActivity(Intent(activity, RealmActivity::class.java))
    }
}