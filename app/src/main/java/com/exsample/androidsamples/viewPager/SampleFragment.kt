package com.exsample.androidsamples.viewPager


import QiitaResponse
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog

import com.exsample.androidsamples.R
import com.exsample.androidsamples.okhttp3.OkHttp3Activity
import com.exsample.androidsamples.okhttp3.QiitaViewAdapter
import com.google.android.gms.common.api.internal.BackgroundDetector.initialize
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_sample.*
import kotlinx.android.synthetic.main.ok_http_activity.*
import okhttp3.*
import timber.log.Timber
import java.io.IOException


/**
 * A simple [Fragment] subclass.
 */
class SampleFragment : Fragment() {
    private val customAdapter by lazy { QiitaViewAdapter(activity!!) }
    private var progressDialog: MaterialDialog? = null
    private val handler = Handler()
    var addpage = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sample, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //処理は、ここから書く　ライフサイクル（アクティビティが生まれて死ぬまで、）
        super.onViewCreated(view, savedInstanceState)
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

        addb.setOnClickListener{
            addpage =addpage + 1
            Toast.makeText(activity!!, "押しました", Toast.LENGTH_SHORT).show()
            updateData()


        }
    }

    private fun initRecyclerView() {
        recyclerView2.apply {
            adapter = customAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout2.setOnRefreshListener {
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
                    swipeRefreshLayout2.isRefreshing = false
                    customAdapter.refresh(listOf())
                }
            }
            override fun onResponse(call: Call, response: Response) {
                handler.post {
                    hideProgress()
                    swipeRefreshLayout2.isRefreshing = false
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
        progressDialog = MaterialDialog(activity!!).apply {
            cancelable(false)
            setContentView(LayoutInflater.from(activity!!).inflate(R.layout.progress_dialog, null, false))
            show()
        }
    }

    private fun hideProgress() {
        progressDialog?.dismiss()
        progressDialog = null
    }
}

















//
//private fun initialize() {
//    initLayout()
//    Timber.d("到着chっく")
//}
//private fun initLayout() {
////                initText()
//    initColor()
//}
////    private fun initText() {
////        textView.text = "${arguments?.getString(KEY_INDEX)}"
////    }
//
//private fun initColor() {
//    context?.also {
//        rootView.setBackgroundColor(ContextCompat.getColor(it, arguments?.getInt(KEY_COLOR) ?: android.R.color.white))
//    }//背景色を変えている
//}
//
//companion object {
//    const val KEY_INDEX = "key_string"
//    const val KEY_COLOR = "key_color"
//}//クラスに対して一個しかない　インスタンスは、三つある 定数置き場
