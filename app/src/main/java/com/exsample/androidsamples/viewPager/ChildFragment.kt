package com.exsample.androidsamples.viewPager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.exsample.androidsamples.R
import com.exsample.androidsamples.recyclerView.RecyclerViewAdapter
import kotlinx.android.synthetic.main.child_fragment.*

class ChildFragment: Fragment() {

    private lateinit var customAdapter : RecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.child_fragment, container, false) // FIXME: setContextViewみたいなもの
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // FIXME: onCreateみたいなもの
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        initLayout()
        initData()
    }

    private fun initLayout() {
        initText()
        initColor()
        initRecyclerView()
    }

    private fun initText() {
        textView.text = "${arguments?.getInt(KEY_INDEX)}"
    }

    private fun initColor() {
        context?.also {
            rootView.setBackgroundColor(ContextCompat.getColor(it, arguments?.getInt(KEY_COLOR) ?: android.R.color.white))
        }
    }

    private fun initRecyclerView() {
        customAdapter = RecyclerViewAdapter(activity!!)
        recycler_view.apply {
            adapter = customAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initData() {
        customAdapter.refresh(makeNamesList())
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
        const val KEY_INDEX = "key_index"
        const val KEY_COLOR = "key_color"
    }
}