package com.exsample.androidsamples.aacApi

import androidx.lifecycle.MutableLiveData
import com.exsample.androidsamples.base.BaseViewModel
import com.exsample.androidsamples.okhttp3.QiitaResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import okhttp3.*

class AacViewModel: BaseViewModel() {

    val items = MutableLiveData<List<QiitaResponse>>()

    val isShownProgress = MutableLiveData<Boolean>()

    fun initData() {
        showProgress()
        updateData()
    }

    fun updateData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://qiita.com/api/v2/items?page=1&per_page=20")
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                hideProgress()
                updateResult(listOf())
            }
            override fun onResponse(call: Call, response: Response) {
                hideProgress()
                response.body?.string()?.also {
                    val gson = Gson()
                    val type = object : TypeToken<List<QiitaResponse>>() {}.type
                    val list = gson.fromJson<List<QiitaResponse>>(it, type)
                    updateResult(list)
                } ?: run {
                    updateResult(listOf())
                }
            }
        })
    }

    private fun updateResult(list: List<QiitaResponse>) {
        items.postValue(list) // 非同期もOK [MainThread以外からもOK]
    }

    private fun showProgress() {
        isShownProgress.postValue(true)
    }

    private fun hideProgress() {
        isShownProgress.postValue(false)
    }
}