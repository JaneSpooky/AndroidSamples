package com.exsample.androidsamples.aacApi

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exsample.androidsamples.R
import com.exsample.androidsamples.databinding.ArticleCellBinding
import com.exsample.androidsamples.okhttp3.QiitaResponse
import com.squareup.picasso.Picasso

class ArticlesView: RecyclerView {

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet?) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr)

    val customAdapter by lazy { Adapter(context) }

    init {
        adapter = customAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
    }

    class Adapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private val items = mutableListOf<QiitaResponse>()

        fun refresh(list: List<QiitaResponse>) {
            items.apply {
                clear()
                addAll(list)
            }
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
//            ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.article_cell, null, false))
            ItemViewHolder(ArticleCellBinding.inflate(LayoutInflater.from(context), parent, false))

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (holder is ItemViewHolder)
                onBindViewHolder(holder, position)
        }

        private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val data = items[position]
            holder.binding.qiitaResponse = data
        }

        class ItemViewHolder(val binding: ArticleCellBinding): RecyclerView.ViewHolder(binding.root)
    }
}