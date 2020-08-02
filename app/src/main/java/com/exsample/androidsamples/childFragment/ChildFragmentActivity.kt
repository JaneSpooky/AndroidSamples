package com.exsample.androidsamples.childFragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import com.exsample.androidsamples.databinding.ChildFragmentActivityBinding
import com.google.android.material.tabs.TabLayoutMediator

class ChildFragmentActivity: BaseActivity() {

    private lateinit var binding: ChildFragmentActivityBinding
    private lateinit var viewModel: ChildFragmentViewModel

    private val customAdapter by lazy { CustomAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    override fun onBackPressed() {
        val selectedPage = binding.viewPager2.currentItem
        val selectedFragment = customAdapter.items[selectedPage].fragment
        val isFragmentBack = when(selectedFragment) {
            is ParentFragment -> selectedFragment.isFragmentBack()
            else -> false
        }
        if (isFragmentBack)
            return
        super.onBackPressed()
    }

    private fun initialize() {
        initBinding()
        initViewModel()
        initLayout()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.child_fragment_activity)
        binding.lifecycleOwner = this
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(ChildFragmentViewModel::class.java).apply {
            selectedIndexNumberPair.observe(this@ChildFragmentActivity, Observer {
                selectNumber(it)
            })
        }
    }

    private fun initLayout() {
        initClick()
        initViewPager2()
        initTabLayout()
    }

    private fun initClick() {
        binding.closeImageView.setOnClickListener {
            finish()
        }
    }

    private fun initViewPager2() {
        binding.viewPager2.apply {
            adapter = customAdapter
            offscreenPageLimit = customAdapter.itemCount
        }
    }

    private fun initTabLayout() {
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager2,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.text = customAdapter.items[position].title
            }
        ).attach()
    }

    private fun selectNumber(pair: Pair<Int, Int>) {
        customAdapter.items.forEach {
            if (it.fragment is ParentFragment)
                it.fragment.showDetail(pair.first, pair.second)
        }
    }

    class CustomAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

        val items = listOf(0, 1, 2).map {
            Item(ParentFragment.newInstance(it), "$it")
        }
        override fun getItemCount(): Int = items.size

        override fun createFragment(position: Int): Fragment = items[position].fragment

        class Item(val fragment: Fragment, val title: String)
    }

    companion object {
        fun start(activity: Activity) = activity.startActivity(Intent(activity, ChildFragmentActivity::class.java))
    }
}