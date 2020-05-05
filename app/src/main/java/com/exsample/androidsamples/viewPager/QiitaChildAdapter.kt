package com.exsample.androidsamples.viewPager

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class QiitaChildAdapter   (private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {//context;Contextって何？

    private val items = mutableListOf<QiitaResponse>()
    //    private val myActivity by lazy {OkHttp3Activity() }
    fun clear(list: List<QiitaResponse>) {
        items.apply {
            clear()//リスト内のデータをクリアする
        }
    }
    fun refresh(list: List<QiitaResponse>) {
        items.apply {
//            clear()//リスト内のデータをクリアする
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size//ここでアイテムの数を把握する

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =//ここで各アイテムのVIewHolderを生成する
        ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.qiita_child_cell,
                parent,
                false

            )
        )
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {//RecyclerViewに対して、ViewHolderが紐付いたときに呼ばれる
        if (holder is ItemViewHolder)
            onBindViewHolder(holder, position)
    }

    private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = items[position]
        holder.titleTextView.text = data.title
        holder.userNameTextView.text = data.user.name
        Picasso.get().load(data.user.profile_image_url).into(holder.imageView)
        holder.rootView.setOnClickListener {
//            Toast.makeText(context, "${data.title}", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, WebView::class.java)
//            // intentにurlを渡すと...
//            context.startActivity(intent);
            intent.putExtra("url",data.url)

            context.startActivity(intent)


        }
    }

    private fun saveRealm(data: QiitaResponse) {
        Realm.getDefaultInstance().executeTransaction { realm ->    //executeTransactionとは、DBに保存するための取引のこと
            var qiitaRealm = QiitaRealm().apply {
                id = data.id
                title = data.title
                body = data.body
                url = data.url

            }
            realm.insertOrUpdate(qiitaRealm)//DBにデータを入れることをinsertという
        }
    }

    private fun saveRealm(list: List<QiitaResponse>) {
        Realm.getDefaultInstance().executeTransaction { realm ->
            list.map {
                QiitaRealm().apply {
                    id = it.id
                    title = it.title
                }
            }.forEach { qiitaRealm ->
                realm.insertOrUpdate(qiitaRealm)
            }
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView: ConstraintLayout = view.findViewById(R.id.child_rootView)
        val imageView: ImageView = view.findViewById(R.id.child_imageView)
        val titleTextView: TextView = view.findViewById(R.id.child_titleTextView)
        val userNameTextView: TextView =  view.findViewById(R.id.child_userNameTextView)
    }
}



