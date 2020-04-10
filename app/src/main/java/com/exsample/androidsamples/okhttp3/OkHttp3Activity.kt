package com.exsample.androidsamples.okhttp3

import QiitaResponse
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.ok_http_activity.*
import okhttp3.*
import timber.log.Timber
import java.io.IOException

class OkHttp3Activity: BaseActivity() {

    private val customAdapter by lazy { QiitaViewAdapter(this) }
    private var progressDialog: MaterialDialog? = null
    private val handler = Handler()
    var addpage = 1
//    var you = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ok_http_activity)
        initialize()
//        println(you)
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
        add.setOnClickListener{
            addpage =addpage + 1
            Toast.makeText(this@OkHttp3Activity, "押しました", Toast.LENGTH_SHORT).show()
            updateData()


        }
    }

    private fun initRecyclerView() {
        recyclerView.apply {
            adapter = customAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            customAdapter.clear(listOf())
            addpage = 1
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
            .url("https://qiita.com/api/v2/items?page=$addpage&per_page=5")//par_page:一回で何件取得するかpage:何ページ目を取得するか
            .build()//$の意味は？ A.変数の中身をstringに突っ込める
        Timber.d("到着チェック")
        println(addpage)

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                handler.post {
                    hideProgress()
                    swipeRefreshLayout.isRefreshing = false
                    customAdapter.refresh(listOf())
                }
            }
            override fun onResponse(call: Call, response: Response) {
                handler.post {
                    hideProgress()
                    swipeRefreshLayout.isRefreshing = false
                    response.body?.string()?.also {
                        val gson = Gson()
                        val type = object : TypeToken<List<QiitaResponse>>() {}.type
                        val list = gson.fromJson<List<QiitaResponse>>(it, type)
                        customAdapter.refresh(list)
                    } ?: run {
                        customAdapter.refresh(listOf())//使える形に変換　Gsonを使用
                    }
                }
            }
        })
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