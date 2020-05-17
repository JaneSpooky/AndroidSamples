package com.exsample.androidsamples.okhttp3

import QiitaResponse
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.ok_http_activity.*
import okhttp3.*
import java.io.IOException

class OkHttp3Activity: BaseActivity() {

    private val customAdapter by lazy { QiitaViewAdapter(this) }
    private var progressDialog: MaterialDialog? = null
    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ok_http_activity)
        initialize()
    }

    private fun initialize() {
        initLayout()
        initData()
    }

    private fun initLayout() {
        initClick()
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    private fun initClick() {
        closeImageView.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            adapter = customAdapter
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3)
        }
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            updateData()
        }
    }

    private fun initData() {
        showProgress()
        updateData()
    }

    private fun updateData() {
        val client = OkHttpClient()
        val request = Request.Builder()
//            .url("https://qiita.com/api/v2/items?per_page=30&page=1")
//            .url("https://api.gnavi.co.jp/RestSearchAPI/?keyid=10d7139a174395ebb2a656fad8ef098a&hit_per_page=30")
            .url("https://api.syosetu.com/novelapi/api/?out=json&biggenre=2")
            .build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.post {
                    hideProgress()
                    swipeRefreshLayout.isRefreshing = false
                    customAdapter.refresh(listOf())
                }
            }
            override fun onResponse(call: Call, response: Response) {
                val list = makeCommonResponseList(response)
                handler.post {
                    hideProgress()
                    swipeRefreshLayout.isRefreshing = false
                    customAdapter.refresh(list)
                }
            }
        })
    }

    private fun makeCommonResponseList(response: Response): List<CommonResponse> {
        response.body?.string()?.also {
            val gson = Gson()
            // Qiita--ここから
//                        val type = object : TypeToken<List<QiitaResponse>>() {}.type
//                        val list = gson.fromJson<List<QiitaResponse>>(it, type).map { CommonResponse().apply {
//                            name = it.title
//                            imageUrl = it.user.profile_image_url
//                        } }
            // Qiita--ここまで
            // ぐるなび--ここから
//            val response = gson.fromJson(it, Food::class.java)
//            val list = response.rest.map { CommonResponse().apply {
//                name = it.name
//                imageUrl = it.image_url.shop_image1
//            } }
            // ぐるなび--ここまで
            // なろう--ここから
            val response = gson.fromJson(it, Narou::class.java)
            val list = response.filterNot { it.title == null }.map { CommonResponse().apply {
                name = it.title
                imageUrl = "からです"
            } }
            // なろう--ここまで
            return list
        }
        return listOf()
    }

    private fun showProgress() {
        hideProgress()
        progressDialog = MaterialDialog(this).apply {
            cancelable(false)
            setContentView(LayoutInflater.from(this@OkHttp3Activity).inflate(R.layout.progress_dialog, null, false))
            show()
        }
    }

    private fun hideProgress() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    companion object {
        fun start(activity: Activity) = activity.startActivity(Intent(activity, OkHttp3Activity::class.java))
    }
}