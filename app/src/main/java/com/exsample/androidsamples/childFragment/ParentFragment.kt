package com.exsample.androidsamples.childFragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseFragment
import com.exsample.androidsamples.databinding.ParentFragmentBinding

class ParentFragment: BaseFragment() {

    private lateinit var binding: ParentFragmentBinding
    private lateinit var viewModel: ParentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = ParentFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this).get(ParentViewModel::class.java).apply {
            fragmentType.observe(viewLifecycleOwner, Observer {
                changeFragment(it)
            })
            index = arguments?.getInt(KEY_INDEX) ?: 0
        }
        binding.index = viewModel.index
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        initLayout()
        initData()
    }

    private fun initLayout() {
        initColor()
    }

    private fun initColor() {
        binding.root.setBackgroundColor(ContextCompat.getColor(requireContext(), COLORS[viewModel.index]))
    }

    private fun initData() {
        viewModel.initData()
    }

    private fun changeFragment(fragmentType: FragmentType) {
        when(fragmentType) {
            FragmentType.NUMBERS ->
                childFragmentManager.beginTransaction()
                    .replace(R.id.container, NumbersFragment.newInstance(viewModel.index))
                    .commit()
            FragmentType.DETAIL ->
                childFragmentManager.beginTransaction()
                    .add(R.id.container, DetailFragment.newInstance(viewModel.index, viewModel.selectedNumber))
                    .addToBackStack(DetailFragment::class.java.simpleName)
                    .commit()
        }
    }

    fun showDetail(index: Int, selectedNumber: Int) {
        viewModel.showDetail(index, selectedNumber)
    }

    fun isFragmentBack(): Boolean {
        val fragment = childFragmentManager.fragments.firstOrNull { it is DetailFragment } ?: return false
        childFragmentManager.beginTransaction().remove(fragment).commit()
        return true
    }

    companion object {
        private const val KEY_INDEX = "key_index"
        private val COLORS = listOf(android.R.color.holo_red_dark, android.R.color.holo_red_light, android.R.color.holo_orange_dark)
        fun newInstance(index: Int): Fragment =
            ParentFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_INDEX, index)
                }
            }
    }
}