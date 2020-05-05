package com.exsample.androidsamples.viewPager

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseFragment
import io.realm.Realm
import kotlinx.android.synthetic.main.child_fragment.*
import kotlinx.android.synthetic.main.fragment_sample.*

class ChildFragment: BaseFragment() {//　Fragmentは、ほとんどアクティビティと一緒
private val customAdapter2 by lazy { QiitaChildAdapter(activity!!) }
    private var progressDialog: MaterialDialog? = null
    private val handler = Handler()
    private lateinit var mRealm : Realm
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //onCreateViewをまず書く　　戻り値がView
        return layoutInflater.inflate(R.layout.child_fragment, container, false)
        //activityでいうところのsetContentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //処理は、ここから書く　ライフサイクル（アクティビティが生まれて死ぬまで、）
        super.onViewCreated(view, savedInstanceState)
        mRealm = Realm.getDefaultInstance()
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


    }

    private fun initRecyclerView() {
        child_recyclerView.apply {
            adapter = customAdapter2
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initSwipeRefreshLayout() {
        child_swipeRefreshLayout.setOnRefreshListener {
            customAdapter2.clear(listOf())
//            addpage = 1
//            updateData()
        }
    }

    private fun initData() {
        showProgress()
    }

//    private fun updateData() {
//
//        val client = OkHttpClient()
//        val request = Request.Builder()
//            .url("https://qiita.com/api/v2/items?page=$addpage&per_page=5")//par_page:一回で何件取得するかpage:何ページ目を取得するか
//            .build()//$の意味は？ A.変数の中身をstringに突っ込める
//        Timber.d("到着チェック")
//        println(addpage)
//
//        client.newCall(request).enqueue(object: Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                handler.post {
//                    hideProgress()
//                    swipeRefreshLayout2.isRefreshing = false
//                    customAdapter.refresh(listOf())
//                }
//            }
//            override fun onResponse(call: Call, response: Response) {
//                handler.post {
//                    hideProgress()
//                    swipeRefreshLayout2.isRefreshing = false
//                    response.body?.string()?.also {
//                        val gson = Gson()
//                        val type = object : TypeToken<List<QiitaResponse>>() {}.type
//                        val list = gson.fromJson<List<QiitaResponse>>(it, type)
//                        customAdapter.refresh(list)
//                    } ?: run {
//                        customAdapter.refresh(listOf())//使える形に変換　Gsonを使用
//                    }
//                }
//            }
//        })
//    }

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
////
//    private fun initColor() {
//        context?.also {
//            rootView.setBackgroundColor(ContextCompat.getColor(it, arguments?.getInt(KEY_COLOR) ?: android.R.color.white))
//        }//背景色を変えている
//    }

//    companion object {
//        const val KEY_INDEX = "key_index"
//        const val KEY_COLOR = "key_color"
//    }//クラスに対して一個しかない　インスタンスは、三つある 定数置き場
