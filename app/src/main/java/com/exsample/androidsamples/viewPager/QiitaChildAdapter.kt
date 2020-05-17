package com.exsample.androidsamples.viewPager

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.exsample.androidsamples.R
import com.exsample.androidsamples.okhttp3.QiitaResponse
import com.exsample.androidsamples.okhttp3.WebView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_sample.*
import okhttp3.*
import timber.log.Timber
import java.io.IOException

class QiitaChildAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {//context;Contextって何？


    private val items = mutableListOf<QiitaRealm>()
    fun clear(list: List<QiitaRealm>) {
        items.apply {
            clear()//リスト内のデータをクリアする
        }
    }
    fun refreshes(list: List<QiitaRealm>) {//これは、initData()みたいに呼び出さなくても勝手に発動するの？　　違うっぽい、、
        items.apply {
            clear()//リスト内のデータをクリアする
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size//ここでアイテムの数を把握する

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =//ここで各アイテムのVIewHolderを生成する
        ItemViewHolder2(
            LayoutInflater.from(context).inflate(
                R.layout.qiita_child_cell,
                parent,
                false

            )
        )
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {//RecyclerViewに対して、ViewHolderが紐付いたときに呼ばれる
        if (holder is ItemViewHolder2)
            onBindViewHolder(holder, position)
    }

    private fun onBindViewHolder(holder: ItemViewHolder2, position: Int) {
        val favoriteList =QiitaRealm.findAll()
        val data = items[position]
        holder.childTitleTextView.text = favoriteList.joinToString{"${it.title}"}
        holder.childNameTextView.text = favoriteList.joinToString{"$it.title"}
        Picasso.get().load(data.imageUrl).into(holder.childImageView)
        holder.childRootView.setOnClickListener {
//            Toast.makeText(context, "${data.title}", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, WebView::class.java)
//            // intentにurlを渡すと...
//            context.startActivity(intent);
            intent.putExtra("url",data.url)
            context.startActivity(intent)
         Timber.d("touchali")
        }
        holder.deleteButton.setOnClickListener {
            callback?.onClickDeleteButton(data)
        }
    }

    var callback : QiitaChildAdapterCallback? = null

    interface QiitaChildAdapterCallback {
        fun onClickDeleteButton(data : QiitaRealm)
    }







    class ItemViewHolder2(view: View) : RecyclerView.ViewHolder(view) {
        val childRootView: ConstraintLayout = view.findViewById(R.id.child_rootView)
        val childImageView: ImageView = view.findViewById(R.id.child_imageView)
        val childTitleTextView: TextView = view.findViewById(R.id.child_titleTextView)
        val childNameTextView: TextView =  view.findViewById(R.id.child_userNameTextView)
        val deleteButton: Button =  view.findViewById(R.id.deleteButton)
    }


}



