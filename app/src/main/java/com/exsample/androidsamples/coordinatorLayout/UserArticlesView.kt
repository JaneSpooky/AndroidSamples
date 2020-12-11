package com.exsample.androidsamples.coordinatorLayout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exsample.androidsamples.databinding.UserArticlesCellBinding
import com.exsample.androidsamples.databinding.UserAtriclesHeaderCellBinding

class UserArticlesView: RecyclerView {

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

        private val items = mutableListOf<String>()

        fun refresh(list: List<String>) {
            items.apply {
                clear()
                addAll(list)
            }
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = items.size + 1

        override fun getItemViewType(position: Int): Int = if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
//            ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.article_cell, null, false))
            when (viewType) {
                VIEW_TYPE_HEADER -> HeaderViewHolder(UserAtriclesHeaderCellBinding.inflate(LayoutInflater.from(context), parent, false))
                else -> ItemViewHolder(UserArticlesCellBinding.inflate(LayoutInflater.from(context), parent, false))
            }


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
           when(holder) {
               is ItemViewHolder -> onBindViewHolder(holder, position)
               is HeaderViewHolder -> onBindViewHolder(holder)
           }
        }

        private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
            val data = items[position - 1]
            holder.binding.data = data
        }

        private fun onBindViewHolder(holder: HeaderViewHolder) {

        }

        class ItemViewHolder(val binding: UserArticlesCellBinding): RecyclerView.ViewHolder(binding.root)
        class HeaderViewHolder(val binding: UserAtriclesHeaderCellBinding): RecyclerView.ViewHolder(binding.root)

        companion object {
            private const val VIEW_TYPE_HEADER = 0
            private const val VIEW_TYPE_ITEM = 1
        }
    }
}