package com.exsample.androidsamples.common

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.exsample.androidsamples.SampleApplication.Companion.applicationContext
import io.reactivex.Observable
import timber.log.Timber

class Prefs {

    val hasToUpdateFcmToken by lazy { BooleanEntry("has_to_update_fcm_token") }
    val fcmToken by lazy { StringEntry("fcm_token") }

    interface Entry<T> {
        fun put(value: T)
        fun get(): Observable<T>
        fun remove()
    }

    class StringEntry(private val key: String, private val defaultValue: String = "") : Entry<String> {
        override fun put(value: String) {
            Timber.d("put $key -> $value")
            getSharedPreference().edit().putString(key, value).apply()
        }

        override fun get(): Observable<String> {
            return createObservable(getSharedPreference(), key) {
                it.getString(key, defaultValue) ?: defaultValue
            }.onErrorReturn {
                defaultValue
            }
        }

        override fun remove() = getSharedPreference().edit().remove(key).apply()

    }

    class BooleanEntry(private val key: String, private val defaultValue: Boolean = false) : Entry<Boolean> {

        override fun put(value: Boolean) {
            Timber.d("put $key -> $value")
            getSharedPreference().edit().putBoolean(key, value).apply()
        }

        override fun get(): Observable<Boolean> {
            return createObservable(getSharedPreference(), key) {
                it.getBoolean(key, defaultValue)
            }.onErrorReturn {
                put(defaultValue)
                defaultValue
            }
        }

        override fun remove() = getSharedPreference().edit().remove(key).apply()
    }

    class EntryNotFoundException : Exception {
        constructor(detailMessage: String?) : super(detailMessage)
    }

    companion object {
        fun getSharedPreference(): SharedPreferences {
            return PreferenceManager.getDefaultSharedPreferences(applicationContext)
        }

        fun <T> createObservable(preferences: SharedPreferences, key: String, valueF: (SharedPreferences) -> T): Observable<T> {
            return Observable.create { subscriber ->
                try {
                    if (preferences.contains(key)) {
                        subscriber.onNext(valueF(preferences))
                        subscriber.onComplete()
                    } else {
                        subscriber.onError(EntryNotFoundException("Not found $key in SharedPreferences"))
                    }
                } catch (e: Exception) {
                    subscriber.onError(e)
                }
            }
        }

    }
}