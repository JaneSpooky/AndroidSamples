package com.exsample.androidsamples.okhttp3

import QiitaResponse
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.exsample.androidsamples.R
import com.squareup.picasso.Picasso

class QiitaViewAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<CommonResponse>()

    fun refresh(list: List<CommonResponse>) {
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
                R.layout.ok_http_cell,
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
        Picasso.get().load(data.imageUrl).into(holder.imageView)
        holder.textView.text = data.name
        holder.rootView.setBackgroundColor(ContextCompat.getColor(context, if (position % 2 == 0) R.color.light_blue else R.color.light_yellow))
        holder.rootView.setOnClickListener {
            Toast.makeText(context, "${data.name}", Toast.LENGTH_SHORT).show()
//            val intent = Intent(context, WebViewActivity::class.java)
//            // intentにurlを渡すと...
//            context.startActivity(intent)
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView: ConstraintLayout = view.findViewById(R.id.rootView)
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val textView: TextView = view.findViewById(R.id.textView)
    }
}