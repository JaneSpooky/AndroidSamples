package com.exsample.androidsamples.main

import androidx.lifecycle.MutableLiveData
import com.exsample.androidsamples.base.BaseViewModel
import com.exsample.androidsamples.firestore.FirestoreActivity
import com.exsample.androidsamples.location.LocationActivity
import com.exsample.androidsamples.okhttp3.OkHttp3Activity
import com.exsample.androidsamples.recyclerView.RecyclerViewActivity
import com.exsample.androidsamples.viewPager.ViewPagerActivity

class MainViewModel: BaseViewModel() {

    val activityPairs = MutableLiveData<List<Pair<String, String>>>()

    fun initData() {
        activityPairs.postValue(
            listOf(
                Pair("RecyclerView", RecyclerViewActivity::class.java.simpleName),
                Pair("ViewPager", ViewPagerActivity::class.java.simpleName),
                Pair("Firestore", FirestoreActivity::class.java.simpleName),
                Pair("OkHttp3", OkHttp3Activity::class.java.simpleName),
                Pair("Location", LocationActivity::class.java.simpleName)
                )
        )
    }
}