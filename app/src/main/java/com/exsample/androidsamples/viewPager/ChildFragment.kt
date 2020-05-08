package com.exsample.androidsamples.viewPager

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseFragment
import io.realm.Realm
import kotlinx.android.synthetic.main.child_fragment.*
import kotlinx.android.synthetic.main.fragment_sample.*
import kotlinx.android.synthetic.main.recycler_view_activity.*
import timber.log.Timber

class ChildFragment: BaseFragment() {//　Fragmentは、ほとんどアクティビティと一緒
private val childCustomAdapter by lazy { QiitaChildAdapter(activity!!) }
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
    }

    private fun initLayout() {
        initClick()
        initRecyclerView()
        initSwipeRefreshLayout()
        initText()
        hideProgress()
    }

    private fun initText(){
//        val favoritList =QiitaRealm.findAll()
//        textView.text = favoritList.joinToString(separator = "/n"){"$it.id"}
    }

    private fun initClick() {


    }

    private fun initRecyclerView() {
        child_recyclerView.apply {
            adapter = childCustomAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initSwipeRefreshLayout() {
        child_swipeRefreshLayout.setOnRefreshListener {
            childCustomAdapter.clear(listOf())
            initData()
        }
    }

    private fun initData() {
        child_swipeRefreshLayout.isRefreshing = false//くるくるを止めるやつ
        Timber.d("ふなっしー")
    }


//    private fun showProgress() {
//        hideProgress()
//        progressDialog = MaterialDialog(activity!!).apply {
//            cancelable(false)
//            setContentView(LayoutInflater.from(activity!!).inflate(R.layout.progress_dialog, null, false))
//            show()
//        }
//    }

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
