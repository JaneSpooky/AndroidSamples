package com.exsample.androidsamples.aacApi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import com.exsample.androidsamples.databinding.AacActivityBinding

class AacActivity: BaseActivity() {

    private lateinit var binding : AacActivityBinding
    private lateinit var viewModel: AacViewModel

    private var progressDialog: MaterialDialog? = null

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
        binding = DataBindingUtil.setContentView(this, R.layout.aac_activity)
        binding.lifecycleOwner = this // 双方向DataBindingを使うのに必要
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(AacViewModel::class.java).apply {
            items.observe(this@AacActivity, Observer {
                binding.apply {
                    articlesView.customAdapter.refresh(it)
                    swipeRefreshLayout.isRefreshing = false
                }
            })
            isShownProgress.observe(this@AacActivity, Observer {
                if (it)
                    showProgress()
                else
                    hideProgress()
            })
        }
    }

    private fun initLayout() {
        initClick()
        initSwipeRefreshLayout()
    }

    private fun initClick() {
        binding.closeImageView.setOnClickListener {
            finish()
        }
    }

    private fun initSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.updateData()
        }
    }

    private fun initData() {
        viewModel.initData()
    }

    private fun showProgress() {
        hideProgress()
        progressDialog = MaterialDialog(this).apply {
            cancelable(false)
            setContentView(LayoutInflater.from(this@AacActivity).inflate(R.layout.progress_dialog, null, false))
            show()
        }
    }

    private fun hideProgress() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    companion object {
        fun start(activity: Activity) = activity.startActivity(Intent(activity, AacActivity::class.java))
    }
}