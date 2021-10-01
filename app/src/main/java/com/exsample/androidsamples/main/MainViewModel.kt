package com.exsample.androidsamples.main

import androidx.lifecycle.MutableLiveData
import com.exsample.androidsamples.aacApi.AacActivity
import com.exsample.androidsamples.base.BaseViewModel
import com.exsample.androidsamples.childFragment.ChildFragmentActivity
import com.exsample.androidsamples.coordinatorLayout.CoordinatorLayoutActivity
import com.exsample.androidsamples.firebaseAuth.FirebaseAuthActivity
import com.exsample.androidsamples.firebaseStorage.FirebaseStorageActivity
import com.exsample.androidsamples.firestore.FirestoreActivity
import com.exsample.androidsamples.location.LocationActivity
import com.exsample.androidsamples.okhttp3.OkHttp3Activity
import com.exsample.androidsamples.recyclerView.RecyclerViewActivity
import com.exsample.androidsamples.recyclerViewSample.RecyclerViewSampleActivity
import com.exsample.androidsamples.service.ServiceActivity
import com.exsample.androidsamples.users.UsersActivity
import com.exsample.androidsamples.viewPager.ViewPagerActivity

class MainViewModel: BaseViewModel() {

    val activityPairs = MutableLiveData<List<Pair<String, String>>>()

    fun initData() {
        activityPairs.postValue(
            listOf(
                Pair("RecyclerView", RecyclerViewActivity::class.java.simpleName),
                Pair("RecyclerViewSample", RecyclerViewSampleActivity::class.java.simpleName),
                Pair("ViewPager", ViewPagerActivity::class.java.simpleName),
                Pair("Firestore", FirestoreActivity::class.java.simpleName),
                Pair("OkHttp3", OkHttp3Activity::class.java.simpleName),
                Pair("Location", LocationActivity::class.java.simpleName),
                Pair("Service", ServiceActivity::class.java.simpleName),
                Pair("FirebaseAuth", FirebaseAuthActivity::class.java.simpleName),
                Pair("FirebaseStorage", FirebaseStorageActivity::class.java.simpleName),
                Pair("AAC", AacActivity::class.java.simpleName),
                Pair("ChildFragment", ChildFragmentActivity::class.java.simpleName),
                Pair("CoordinatorLayout", CoordinatorLayoutActivity::class.java.simpleName),
                Pair("Users", UsersActivity::class.java.simpleName)
                )
        )
    }
}