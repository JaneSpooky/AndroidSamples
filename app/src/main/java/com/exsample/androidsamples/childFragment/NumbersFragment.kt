package com.exsample.androidsamples.childFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.exsample.androidsamples.base.BaseFragment

class NumbersFragment: BaseFragment() {



    companion object {
        private const val KEY_INDEX = "key_index"
        private val COLORS = listOf(android.R.color.holo_blue_dark, android.R.color.holo_blue_light, android.R.color.holo_blue_bright)
        fun newInstance(index: Int): Fragment =
            NumbersFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_INDEX, index)
                }
            }
    }
}