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
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.chat_room_fragment.*

class ChatRoomFragment: BaseFragment() {

    private val customAdapter by lazy { ChatRoomAdapter(context) }

    private var roomId = ""
    private var messageListener: ListenerRegistration? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.chat_room_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        roomId = arguments?.getString(KEY_ROOM_ID) ?: return { activity?.onBackPressed() }()
        initLayout()
        initData()
    }

    private fun initLayout() {
        initText()
        initClick()
        initRecyclerView()
        initSwipeRefreshLayout()
    }

    private fun initText() {
        titleTextView.text = arguments?.getString(KEY_ROOM_NAME) ?: ""
    }

    private fun initClick() {
        closeImageView.setOnClickListener {
            activity?.onBackPressed()
        }
        makeCommentTextView.setOnClickListener {
            showMakeCommentDialog()
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

    private fun showMakeCommentDialog() {
        context?.also {
            MaterialDialog(it).show {
                title(R.string.enter_chat_message)
                input(inputType = InputType.TYPE_CLASS_TEXT) { _, text ->
                    makeComment("$text")
                }
                positiveButton(R.string.ok)
                negativeButton(R.string.cancel)
            }
        }
    }

    private fun makeComment(text: String) {
        FirebaseFirestore.getInstance()
            .collection("messages")
            .add(ChatMessage().apply {
                message = text
                roomId = this@ChatRoomFragment.roomId
            })
    }

    private fun initData() {
        FirebaseFirestore.getInstance()
            .collection("messages")
            .whereEqualTo(ChatMessage::roomId.name, roomId)
            .get()
            .addOnCompleteListener {
                swipeRefreshLayout.isRefreshing = false
                if (!it.isSuccessful)
                    return@addOnCompleteListener
                it.result?.toObjects(ChatMessage::class.java)?.also { messages ->
                    customAdapter.refresh(messages)
                }
                initSubscribe()
            }
    }

    private fun initSubscribe() {
        messageListener = FirebaseFirestore.getInstance()
            .collection("messages")
            .whereEqualTo(ChatMessage::roomId.name, roomId)
            .orderBy(ChatMessage::createdAt.name, Query.Direction.DESCENDING)
            .limit(1L)
            .addSnapshotListener { snapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    firebaseFirestoreException.printStackTrace()
                    return@addSnapshotListener
                }
                snapshot?.toObjects(ChatMessage::class.java)?.firstOrNull()?.also {
                    customAdapter.add(it)
                }
            }
    }

    companion object {
        const val KEY_ROOM_ID = "key_room_id"
        const val KEY_ROOM_NAME = "key_room_name"
    }
}