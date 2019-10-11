package com.exsample.androidsamples.main

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import com.exsample.androidsamples.databinding.MainActivityBinding
import com.exsample.androidsamples.recyclerView.RecyclerViewActivity
import com.exsample.androidsamples.viewPager.ViewPagerActivity

class MainActivity: BaseActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        initBinding()
        initViewModel()
        initLayout()
        initData()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity)
        binding.lifecycleOwner = this
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java).apply {
            activityPairs.observe(this@MainActivity, Observer {
                binding.samplesView.customAdapter.refresh(it)
            })
        }
    }

    private fun initLayout() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.samplesView.customAdapter.callback = object : SamplesView.Callback {
            override fun onClick(activitySimpleName: String) {
                startActivity(activitySimpleName)
            }
        }
    }

    private fun startActivity(activitySimpleName: String) {
        when(activitySimpleName) {
            RecyclerViewActivity::class.java.simpleName -> RecyclerViewActivity.start(this, 1, 2)
            ViewPagerActivity::class.java.simpleName -> ViewPagerActivity.start(this)

        }
    }

    private fun initData() {
        viewModel.initData()
    }
}