package com.exsample.androidsamples.viewPager


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import com.exsample.androidsamples.R
import com.google.android.gms.common.api.internal.BackgroundDetector.initialize
import kotlinx.android.synthetic.main.fragment_sample.*
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class sampleFragment : Fragment() {

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
        Timber.d("到着chっく")
    }
    private fun initLayout() {
                initText()
        initColor()
    }
    private fun initText() {
        textView.text = "${arguments?.getInt(KEY_INDEX)}"
    }

    private fun initColor() {
        context?.also {
            rootView.setBackgroundColor(ContextCompat.getColor(it, arguments?.getInt(KEY_COLOR) ?: android.R.color.white))
        }//背景色を変えている
    }

    companion object {
        const val KEY_INDEX = "key_index"
        const val KEY_COLOR = "key_color"
    }//クラスに対して一個しかない　インスタンスは、三つある 定数置き場
}
