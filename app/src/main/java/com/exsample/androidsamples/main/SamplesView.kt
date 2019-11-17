package com.exsample.androidsamples.main

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.exsample.androidsamples.databinding.SampleCellBinding
import java.util.*

class SamplesView: RecyclerView {

    constructor(ctx: Context) : super(ctx)
    constructor(ctx: Context, attrs: AttributeSet?) : super(ctx, attrs)
    constructor(ctx: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(ctx, attrs, defStyleAttr)

    val customAdapter by lazy { Adapter(context) }

    init {
        adapter = customAdapter
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
    }

    class Adapter(val context: Context) : RecyclerView.Adapter<ViewHolder>() {

        val items = mutableListOf<Pair<String, String>>()

        var callback: Callback? = null


        fun refresh(list: List<Pair<String, String>>) {
            items.apply {
                clear()
                addAll(list)
            }
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int = items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            SampleViewModel(SampleCellBinding.inflate(LayoutInflater.from(context), parent, false))

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (holder is SampleViewModel)
                onBindViewHolder(holder, position)

        }

        private fun onBindViewHolder(holder: SampleViewModel, position: Int) {
            val data = items[position]
            holder.binding.apply {
                title = data.first
                root.setOnClickListener { callback?.onClick(data.second) }
            }
        }
    }

    class SampleViewModel(val binding: SampleCellBinding): RecyclerView.ViewHolder(binding.root)

    interface Callback {

        fun onClick(activitySimpleName: String)
    }
}