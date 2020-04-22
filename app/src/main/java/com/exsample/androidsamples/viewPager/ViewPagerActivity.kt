package com.exsample.androidsamples.viewPager

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.exsample.androidsamples.R
import com.exsample.androidsamples.okhttp3.QiitaViewAdapter
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
            finish()//activityで使えるメゾット
        }
    }

    private fun initViewPager() {
        viewPager.apply {
            adapter = customAdapter
            offscreenPageLimit = customAdapter.count
            //何ページ保持するか　この場合は全ページ
        }
    }

    private fun initTabLayout() {
        tabLayout.setupWithViewPager(viewPager)
    }


    class CustomAdapter(fragmentManager: FragmentManager) :
    //CustomAdapterの正体はFragmentPagerAdapter
        FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        //第一引数fragmentManager 第二引数BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        private val items =
            listOf(Item(SampleFragment(), "一覧"), Item(ChildFragment(), "お気に入り"))
//            .map{
//                Item(SampleFragment(), it)
//                Item(ChildFragment(),it)
//
//            } // タイトルのリストからItemのリストへ変換
        override fun getCount(): Int = items.size
        override fun getItem(position: Int): Fragment = items[position].fragment
        override fun getPageTitle(position: Int): CharSequence? = items[position].title // これでTabLayoutのタイトルが返ります
        class Item(val fragment: Fragment, val title: String)

//        private val items: List<Item> = listOf(
//           Triple("リサイクラービュー",R.color.light_yellow,""),
//            Triple("お気に入りフラグメント",R.color.light_yellow,"")
//
//        )
//            .map {
//                Item(
//                    SampleFragment().apply {
//                        //変数をいじるapply
//                        arguments = Bundle().apply {
//                            //argumentsは、Bundle型、、　↪値を入れるために特化したもの
//                            putString(SampleFragment.KEY_INDEX, it.first)//firstは、Pair(0,R.color.light_blue)の第一引数、を表している
//                            putInt(SampleFragment.KEY_COLOR, it.second)
//                            //Fragmentへの値の渡し方　「決まっている物」
//                        }
//                    }
//                )
//            }

//        override fun getCount(): Int = items.size
//        //sizeは何をあらわしている？A.スライドが何枚あるのか
//        override fun getItem(position: Int): Fragment = items[position].fragment
//        //getItemにはFragmentを書く
//        override fun getPageTitle(position: Int): CharSequence? ="$position"
//        //tabレイアウトに入る文字
//        class Item(val fragment: Fragment)


    }

    companion object {
        fun start(activity: Activity) =
            activity.startActivity(Intent(activity, ViewPagerActivity::class.java))
    }
}


