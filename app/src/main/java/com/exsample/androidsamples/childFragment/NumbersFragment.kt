package com.exsample.androidsamples.childFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.exsample.androidsamples.base.BaseFragment
import com.exsample.androidsamples.databinding.NumbersFragmentBinding

class NumbersFragment: BaseFragment() {

    private lateinit var binding: NumbersFragmentBinding
    private lateinit var viewModel: ChildFragmentViewModel

    private var index = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        index = arguments?.getInt(KEY_INDEX) ?: 0
        binding = NumbersFragmentBinding.inflate(inflater, container, false)
        binding.index = index
        viewModel = ViewModelProviders.of(requireActivity()).get(ChildFragmentViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        initClick()
        initColor()
    }

    private fun initClick() {
        binding.apply {
            button0.setOnClickListener {
                selectNumber(0)
            }
            button1.setOnClickListener {
                selectNumber(1)
            }
            button2.setOnClickListener {
                selectNumber(2)
            }
        }
    }

    private fun selectNumber(number: Int) {
        viewModel.selectNumber(index, number)
    }

    private fun initColor() {
        binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), COLORS[index]))
    }

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