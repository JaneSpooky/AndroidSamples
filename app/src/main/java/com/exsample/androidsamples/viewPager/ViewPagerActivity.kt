package com.exsample.androidsamples.viewPager

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.exsample.androidsamples.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.view_pager_activity.*

class ViewPagerActivity : AppCompatActivity() {

    private val customAdapter by lazy { CustomAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_pager_activity)
        initialize()
    }

    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        initClick()
        initViewPager()
        initTabLayout()
    }

    private fun initClick() {
        closeImageView.setOnClickListener {
            finish()
        }
    }

    private fun initViewPager() {
        viewPager.apply {
            adapter = customAdapter
            offscreenPageLimit = customAdapter.itemCount
        }
    }

    private fun initTabLayout() {
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = customAdapter.items[position].title
        }.attach()
    }

    class CustomAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        val items: List<Item> = listOf(
            Pair(0, R.color.light_blue),
            Pair(1, R.color.light_yellow),
            Pair(2, R.color.light_blue)
        )
            .map {
                Item(
                    ChildFragment().apply {
                        arguments = Bundle().apply {
                            putInt(ChildFragment.KEY_INDEX, it.first)
                            putInt(ChildFragment.KEY_COLOR, it.second)
                        }
                    },
                    "${it.first}"
                )
            }

        override fun getItemCount(): Int = items.size

        override fun createFragment(position: Int): Fragment = items[position].fragment
        class Item(val fragment: Fragment, val title: String)
    }

    companion object {
        fun start(activity: Activity) =
            activity.startActivity(Intent(activity, ViewPagerActivity::class.java))
    }
}
