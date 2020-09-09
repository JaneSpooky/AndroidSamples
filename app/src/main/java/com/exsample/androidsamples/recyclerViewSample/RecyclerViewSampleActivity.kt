package com.exsample.androidsamples.recyclerViewSample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import kotlinx.android.synthetic.main.recycler_view_sample_activity.*

class RecyclerViewSampleActivity: BaseActivity() {

    private val customAdapter by lazy { SampleRecyclerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view_sample_activity)
        initialize()
    }

    private fun initialize() {
        initLayout()
        initData()
    }

    private fun initLayout() {
        initClick()
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    private fun initClick() {
        closeImageView.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerView() {
        customAdapter.onClick = {
            Toast.makeText(this, "name:${it.name}", Toast.LENGTH_SHORT).show()
        }
        recyclerView.apply {
            adapter = customAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            initData()
        }
    }

    private fun initData() {
        customAdapter.refresh(makeNamesList())
        swipeRefreshLayout.isRefreshing = false
    }

    private fun makeNamesList(): List<User> {
        val list = mutableListOf<User>()
        val max = (Math.random() * 50).toInt() + 1
        for (i in 0 .. max) {
            list.add(User().apply { makeData() })
        }
        return list
    }

    companion object {
        fun start(activity: Activity) = activity.startActivity(Intent(activity, RecyclerViewSampleActivity::class.java))
    }
}