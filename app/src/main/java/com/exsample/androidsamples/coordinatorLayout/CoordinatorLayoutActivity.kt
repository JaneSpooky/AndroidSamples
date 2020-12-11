package com.exsample.androidsamples.coordinatorLayout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import com.exsample.androidsamples.databinding.CoordinatorLayoutActivityBinding

class CoordinatorLayoutActivity : BaseActivity() {

    private lateinit var binding: CoordinatorLayoutActivityBinding
    private val viewModel: CoordinatorLayoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

    private fun initialize() {
        initBinding()
        initViewModel()
        initData()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.coordinator_layout_activity)
        binding.lifecycleOwner = this
    }

    private fun initViewModel() {
        viewModel.apply {
            items.observe(this@CoordinatorLayoutActivity, Observer {
                binding.userArticlesView.customAdapter.refresh(it)
            })
        }
    }

    private fun initData() {
        viewModel.initData()
    }

    companion object {
        fun start(activity: Activity) =
            activity.startActivity(Intent(activity, CoordinatorLayoutActivity::class.java))
    }
}