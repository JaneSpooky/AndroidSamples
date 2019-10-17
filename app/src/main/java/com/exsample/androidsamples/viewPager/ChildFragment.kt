package com.exsample.androidsamples.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseFragment
import kotlinx.android.synthetic.main.child_fragment.*

class ChildFragment: BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.child_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        initLayout()
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
        }
    }

    companion object {
        const val KEY_INDEX = "key_index"
        const val KEY_COLOR = "key_color"
    }
}