package com.exsample.androidsamples.todo

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.datetime.dateTimePicker
import com.exsample.androidsamples.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.todo_view_cell.*
import timber.log.Timber
import java.util.*

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
        val res = StateListDrawable()
        holder.nameTextView.text = data.name
//        holder.todoDateTextView.text = DateFormat.format("yyyy/MM/dd hh:mm:ss", data.createdAt)
        holder.rootView.setOnClickListener {
            callback?.onClick(data)
        }
        holder.timeText.text =( DateFormat.format("MM/dd hh:mm", data.createdAt))
        holder.deadTime.text =( DateFormat.format("MM/dd hh:mm", data.deadLineAt))
        holder.deadTime.setOnClickListener{
            MaterialDialog(context!!).show {
                dateTimePicker(requireFutureDateTime = true) { _, dateTime ->
                    var calendar = dateTime
                    val unixTimeFromCalendar = calendar.getTimeInMillis()
                    var date = Date().apply { time = unixTimeFromCalendar }
                    deadLineData(date)
                    // Use dateTime (Calendar)
                }
            }
        }
        holder.todoImageButton.setOnClickListener{
           when(data.completed){
               true ->{ data.completed=false }
               false ->{data.completed =true }
           }
            callback?.onClick(data)
           }
        when(data.completed){
            true ->{ holder.todoImageButton.setBackgroundResource(R.drawable.yet_completed) }
            false ->{ holder.todoImageButton.setBackgroundResource(R.drawable.already_completed)
                Timber.d("達成済" )
            }
        }

        }


    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val rootView: ConstraintLayout = view.findViewById(R.id.rootView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val timeText: TextView = view.findViewById(R.id.startText)
        val todoImageButton: ImageButton = view.findViewById(R.id.TodoImageButton)
        val deadTime: TextView = view.findViewById(R.id.deadTime)
    }
    private fun deadLineData(deadData:Date?){
        val todo = Todo().apply {
            deadLineAt = deadData
        }
        FirebaseFirestore.getInstance()
            .collection("todo")
//            .set(todo)
    }


    interface TodoAdapterCallback {
        fun onClick(data: Todo)
    }
}