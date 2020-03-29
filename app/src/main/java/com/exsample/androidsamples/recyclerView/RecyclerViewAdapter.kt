package com.exsample.androidsamples.recyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.exsample.androidsamples.R

class RecyclerViewAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<String>()

    fun refresh(list: List<String>) {
        items.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size//

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recycler_view_cell,
                parent
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            onBindViewHolder(holder, position)
    }

    private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = items[position]
        holder.nameTextView.text = data
        holder.rootView.setBackgroundColor(ContextCompat.getColor(context, if (position % 2 == 0) R.color.light_blue else R.color.light_yellow))
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView: ConstraintLayout = view.findViewById(R.id.rootView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
    }
}