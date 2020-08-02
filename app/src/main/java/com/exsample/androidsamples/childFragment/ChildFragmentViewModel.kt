package com.exsample.androidsamples.childFragment

import androidx.lifecycle.MutableLiveData
import com.exsample.androidsamples.base.BaseViewModel

class ChildFragmentViewModel: BaseViewModel() {

    val selectedIndexNumberPair = MutableLiveData<Pair<Int, Int>>()

    fun selectNumber(index: Int, number: Int) {
        selectedIndexNumberPair.postValue(Pair(index, number))
    }
}