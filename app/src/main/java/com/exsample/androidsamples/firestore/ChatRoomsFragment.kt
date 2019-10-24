package com.exsample.androidsamples.firestore

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseFragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.chat_rooms_fragment.*
import timber.log.Timber

class ChatRoomsFragment: BaseFragment() {

    private val customAdapter by lazy { ChatRoomsAdapter(context) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.chat_rooms_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            activity?.onBackPressed()
        }
        makeRoomTextView.setOnClickListener {
            showMakeRoomDialog()
        }
    }

    private fun initRecyclerView() {
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
        context?.also {
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
        FirebaseFirestore.getInstance()
            .collection("rooms")
            .add(ChatRoom().apply {
                name = roomName
            })
            .addOnCompleteListener {
                initData()
            }
    }

    private fun initData() {
        FirebaseFirestore.getInstance()
            .collection("rooms")
            .get()
            .addOnCompleteListener {
                swipeRefreshLayout.isRefreshing = false
                if (!it.isSuccessful)
                    return@addOnCompleteListener
                it.result?.toObjects(ChatRoom::class.java)?.also { chatRooms ->
                    customAdapter.refresh(chatRooms)
                }
            }.addOnFailureListener {
                it.printStackTrace()
                Timber.e("${it.message}")
            }
    }
}