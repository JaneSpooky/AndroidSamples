package com.exsample.androidsamples.recyclerView

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import kotlinx.android.synthetic.main.recycler_view_activity.*

class RecyclerViewActivity: BaseActivity() {

    private val customAdapter by lazy { RecyclerViewAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view_activity)
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

    private fun makeNamesList(): List<String> {
        val list = mutableListOf<String>()
        val max = (Math.random() * 50).toInt() + 1
        for (i in 0 .. max) {
            list.add("$i")
        }
        return list
    }

    companion object {
        fun start(activity: Activity) = activity.startActivity(Intent(activity, RecyclerViewActivity::class.java))
    }
}