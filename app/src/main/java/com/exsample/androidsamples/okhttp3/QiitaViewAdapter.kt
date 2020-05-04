package com.exsample.androidsamples.okhttp3

import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.exsample.androidsamples.R
import com.exsample.androidsamples.viewPager.QiitaRealm
import com.exsample.androidsamples.viewPager.SampleFragment
import com.squareup.picasso.Picasso
import io.realm.Realm
import timber.log.Timber


class QiitaViewAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {//context;Contextって何？

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
                R.layout.qiita_view_cell,
                parent,
                false

            )
        )
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {//RcyclerViewに対して、ViewHolderが紐付いたときに呼ばれる
        if (holder is ItemViewHolder)
            onBindViewHolder(holder, position)
    }

    private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = items[position]
        holder.titleTextView.text = data.title
        holder.likeCountTextView.text = "${data.likes_count}"//$何とかで文字列ではなくデータの中身を見せろという意味
        holder.userNameTextView.text = data.user.name
        Picasso.get().load(data.user.profile_image_url).into(holder.imageView)
        holder.clipmaster_errer.setOnClickListener{
            Toast.makeText(context, "爆弾処理完了", Toast.LENGTH_SHORT).show()
            saveRealm(data)
        }
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
        Realm.getDefaultInstance().executeTransaction { realm ->
            var qiitaRealm = QiitaRealm().apply {
                id = data.id
                title = data.title
                body = data.body
            }
            realm.insertOrUpdate(qiitaRealm)
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
        val rootView: ConstraintLayout = view.findViewById(R.id.rootView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val likeCountTextView: TextView = view.findViewById(R.id.likeCountTextView)
        val userNameTextView:TextView =  view.findViewById(R.id.userNameTextView)
        val clipmaster_errer : ImageView = view.findViewById(R.id.clipmaster_errer)
    }
}