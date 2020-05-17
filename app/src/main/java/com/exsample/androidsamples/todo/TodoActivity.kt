package com.exsample.androidsamples.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import com.exsample.androidsamples.firestore.ChatRoom
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.chat_rooms_fragment.*
import java.util.*

class TodoActivity: BaseActivity() {

    private val customAdapter by lazy { TodoAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chat_rooms_fragment)
        initialize()
    }

    private fun initialize() {
        initLayout()
        initData()
    }

    private fun initLayout() {
        initClick()
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    private fun initClick() {
        closeImageView.setOnClickListener {
            this?.onBackPressed()
        }
        makeRoomTextView.setOnClickListener {
            showMakeRoomDialog()
        }
    }

    private fun initRecyclerView() {
        customAdapter.callback = object: TodoAdapter.TodoAdapterCallback {
            override fun onClick(data: Todo) {
                updateTodo(data)
            }
        }
        recyclerView.apply {
            adapter = customAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            initData()
        }
    }

    private fun showMakeRoomDialog() {
        this?.also {
            MaterialDialog(it).show {
                title(R.string.enter_chat_room_name)
                input(inputType = InputType.TYPE_CLASS_TEXT) { _, text ->
                    makeRoom("$text")
                }
                positiveButton(R.string.ok)
                negativeButton(R.string.cancel)
            }
        }
    }

    private fun makeRoom(roomName: String) {
        val todo = Todo().apply {
            name = roomName
        }
        FirebaseFirestore.getInstance()
            .collection("todo")
            .document("${todo.id}")
            .set(todo)
            .addOnCompleteListener {
                initData()
            }
    }

    private fun initData() {
        FirebaseFirestore.getInstance()
            .collection("todo")
            .get()
            .addOnCompleteListener {
                swipeRefreshLayout.isRefreshing = false
                if (!it.isSuccessful)
                    return@addOnCompleteListener
                it.result?.toObjects(Todo::class.java)?.also { chatRooms ->
                    customAdapter.refresh(chatRooms)
                }
            }
    }


    private fun updateTodo(data: Todo) {
        FirebaseFirestore.getInstance()
            .collection("todo")
            .document("${data.id}")
            .set(data.apply {
                updatedAt = Date()
            })
            .addOnCompleteListener {
                initData()
            }
    }

    companion object {
        fun start(activity: Activity) = activity.startActivity(Intent(activity, TodoActivity::class.java))
    }
}