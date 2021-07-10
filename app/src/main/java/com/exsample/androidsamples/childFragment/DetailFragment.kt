package com.exsample.androidsamples.childFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.exsample.androidsamples.base.BaseFragment
import com.exsample.androidsamples.databinding.DetailFragmentBinding

class DetailFragment: BaseFragment() {

    private lateinit var binding: DetailFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val index = arguments?.getInt(KEY_INDEX) ?: 0
        val number = arguments?.getInt(KEY_NUMBER) ?: 0
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        binding.index = index
        binding.number = number
        binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), COLORS[index]))
        return binding.root
    }

    companion object {
        private const val KEY_INDEX = "key_index"
        private const val KEY_NUMBER = "key_number"
        private val COLORS = listOf(android.R.color.holo_green_dark, android.R.color.holo_green_light, android.R.color.holo_purple)
        fun newInstance(index: Int, number: Int): Fragment =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_INDEX, index)
                    putInt(KEY_NUMBER, number)
                }
            }
    }
}