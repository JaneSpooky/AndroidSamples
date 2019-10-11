package com.exsample.androidsamples.recyclerView

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.exsample.androidsamples.R

class RecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<String>()

    fun refresh(list: List<String>) {
        items.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size + 1

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return VIEW_TYPE_HEADER
        else
            return VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_HEADER) {
            return HeaderViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.header_cell,
                    parent,
                    false
                )
            )
        } else {
            return ItemViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.recycler_view_cell,
                    parent,
                    false
                )
            )
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            onBindItemViewHolder(holder, position)
        else if (holder is HeaderViewHolder)
            onBindHeaderViewHolder(holder)
    }

    private fun onBindItemViewHolder(holder: ItemViewHolder, position: Int) {
        val data = items[position - 1]
        holder.nameTextView.text = data
        val colorId =
            if (position % 2 == 0)
                R.color.light_blue
            else
                R.color.light_yellow
        holder.rootView.setBackgroundColor(
            ContextCompat.getColor(context, colorId)
        )
    }

    private fun onBindHeaderViewHolder(holder: HeaderViewHolder) {
        holder.nameTextView.text = DateFormat.format("yyyy年MM月dd日 hh:mm:ss", System.currentTimeMillis())
    }

    class HeaderViewHolder(view:  View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView: ConstraintLayout = view.findViewById(R.id.rootView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 1
        private const val VIEW_TYPE_ITEM = 2
    }
}