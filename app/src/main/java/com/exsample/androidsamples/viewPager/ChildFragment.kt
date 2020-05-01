package com.exsample.androidsamples.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseFragment
import io.realm.Realm

class ChildFragment: BaseFragment() {//　Fragmentは、ほとんどアクティビティと一緒

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
        initLayout()//ココをわざわざ分割する必要があるのか
    }

    private fun initLayout() {
//        initText()
//        initColor()
    }


//    private fun initText() {
//        textView.text = "${arguments?.getInt(KEY_INDEX)}"
//    }
//
//    private fun initColor() {
//        context?.also {
//            rootView.setBackgroundColor(ContextCompat.getColor(it, arguments?.getInt(KEY_COLOR) ?: android.R.color.white))
//        }//背景色を変えている
//    }

//    companion object {
//        const val KEY_INDEX = "key_index"
//        const val KEY_COLOR = "key_color"
//    }//クラスに対して一個しかない　インスタンスは、三つある 定数置き場
    }