package com.exsample.androidsamples.coordinatorLayout

import androidx.lifecycle.MutableLiveData
import com.exsample.androidsamples.base.BaseViewModel
import java.util.*

class CoordinatorLayoutViewModel: BaseViewModel() {

    val items = MutableLiveData<List<String>>()

    fun initData() {
        val list = mutableListOf<String>()
        for (i in 0 .. 100) {
            list.add(UUID.randomUUID().toString())
        }
        items.postValue(list)
    }
}