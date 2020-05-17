package com.exsample.androidsamples.todo

import com.exsample.androidsamples.firestore.ChatRoom
import com.exsample.androidsamples.firestore.EventManager

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.exsample.androidsamples.R

class TodoAdapter(private val context: Context?)  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Todo>()

    var callback : TodoAdapterCallback? = null

    fun refresh(list: List<Todo>) {
        items.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.todo_view_cell,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            onBindViewHolder(holder, position)
    }

    private fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val data = items[position]
        holder.nameTextView.text = data.name
        holder.dateTextView.text = DateFormat.format("yyyy/MM/dd hh:mm:ss", data.createdAt)
        holder.rootView.setOnClickListener {
            callback?.onClick(data)
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView: ConstraintLayout = view.findViewById(R.id.rootView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        var dateTextView: TextView = view.findViewById(R.id.dateTextView)
    }

    interface TodoAdapterCallback {
        fun onClick(data: Todo)
    }
}