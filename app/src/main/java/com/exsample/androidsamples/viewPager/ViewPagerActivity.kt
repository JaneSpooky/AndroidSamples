package com.exsample.androidsamples.viewPager

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.exsample.androidsamples.R
import kotlinx.android.synthetic.main.view_pager_activity.*

class ViewPagerActivity : AppCompatActivity() {

    private val customAdapter by lazy { CustomAdapter(supportFragmentManager) }

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
            offscreenPageLimit = customAdapter.count
        }
    }

    private fun initTabLayout() {
        tabLayout.setupWithViewPager(viewPager)
    }

    class CustomAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val items: List<Item> = listOf(
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
                    }
                )
            }

        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Fragment = items[position].fragment
        override fun getPageTitle(position: Int): CharSequence? = "$position"
        class Item(val fragment: Fragment)
    }

    companion object {
        fun start(activity: Activity) =
            activity.startActivity(Intent(activity, ViewPagerActivity::class.java))
    }
}
