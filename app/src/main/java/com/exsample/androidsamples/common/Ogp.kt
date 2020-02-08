package com.exsample.androidsamples.common

import android.annotation.SuppressLint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import timber.log.Timber

class Ogp {


    // 非同期をRxでやる
    @SuppressLint("CheckResult")
    private fun getOgp(url: String, onSuccess : (Elements) -> Unit, onError: (Throwable) -> Unit) {
        Observable.just(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                Jsoup.connect(url).get()
            }
            .map {
                it.select("meta[property~=og:*]")
            }
            .subscribe({
                onSuccess.invoke(it)
            }, {
                onError.invoke(it)
            })
    }

    // 使い方
    private fun sampleDo() {
        getOgp("https://www.google.com/", {
            it.forEach { element ->
                Timber.d(element.attr("property"))
                Timber.d(element.attr("content"))
            }
        }, {
            it.printStackTrace()
        })
    }
}