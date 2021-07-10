package com.exsample.androidsamples.main

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.exsample.androidsamples.R
import com.exsample.androidsamples.aacApi.AacActivity
import com.exsample.androidsamples.base.BaseActivity
import com.exsample.androidsamples.childFragment.ChildFragmentActivity
import com.exsample.androidsamples.coordinatorLayout.CoordinatorLayoutActivity
import com.exsample.androidsamples.databinding.MainActivityBinding
import com.exsample.androidsamples.firebaseAuth.FirebaseAuthActivity
import com.exsample.androidsamples.firebaseStorage.FirebaseStorageActivity
import com.exsample.androidsamples.firestore.FirestoreActivity
import com.exsample.androidsamples.location.LocationActivity
import com.exsample.androidsamples.okhttp3.OkHttp3Activity
import com.exsample.androidsamples.recyclerView.RecyclerViewActivity
import com.exsample.androidsamples.recyclerViewSample.RecyclerViewSampleActivity
import com.exsample.androidsamples.service.ServiceActivity
import com.exsample.androidsamples.viewPager.ViewPagerActivity

class MainActivity: BaseActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        // ここで何か処理をします。
        doSomething()
    }

    private fun doSomething()  {
        Log.d("たぐです", "なにかやります")
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
            RecyclerViewActivity::class.java.simpleName -> RecyclerViewActivity.start(this)
            RecyclerViewSampleActivity::class.java.simpleName -> RecyclerViewSampleActivity.start(this)
            ViewPagerActivity::class.java.simpleName -> ViewPagerActivity.start(this)
            FirestoreActivity::class.java.simpleName -> FirestoreActivity.start(this)
            OkHttp3Activity::class.java.simpleName -> OkHttp3Activity.start(this)
            LocationActivity::class.java.simpleName -> LocationActivity.start(this)
            ServiceActivity::class.java.simpleName -> ServiceActivity.start(this)
            FirebaseAuthActivity::class.java.simpleName -> FirebaseAuthActivity.start(this)
            FirebaseStorageActivity::class.java.simpleName -> FirebaseStorageActivity.start(this)
            AacActivity::class.java.simpleName -> AacActivity.start(this)
            ChildFragmentActivity::class.java.simpleName -> ChildFragmentActivity.start(this)
            CoordinatorLayoutActivity::class.java.simpleName -> CoordinatorLayoutActivity.start(this)
        }
    }

    private fun initData() {
        viewModel.initData()
    }
}