package com.exsample.androidsamples.okhttp3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exsample.androidsamples.R
import kotlinx.android.synthetic.main.qiita_view_cell.*


class WebView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

//        val web_view = intent.getStringExtra("url")

        val webView =
            view.findViewById(R.id.web_view) as WebView

    }

}
