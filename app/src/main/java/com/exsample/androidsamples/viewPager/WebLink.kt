package com.exsample.androidsamples.viewPager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exsample.androidsamples.R
import kotlinx.android.synthetic.main.activity_web_view.*
import timber.log.Timber

class WebLink : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_web_link)
        web_view.loadUrl(intent.getStringExtra("url"))
        Timber.d("到着チェック")//Logとの違いは？　A.引数が二ついる
        //Logを見やすくする方法　A.file→settings
    }
}