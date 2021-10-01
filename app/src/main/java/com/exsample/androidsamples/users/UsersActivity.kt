package com.exsample.androidsamples.users

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exsample.androidsamples.R
import com.exsample.androidsamples.firebaseAuth.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.firebase_auth_activity.closeImageView
import kotlinx.android.synthetic.main.users_activity.*

class UsersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.users_activity)
        initialize()
    }

    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        initClick()
    }

    private fun initClick() {
        closeImageView.setOnClickListener {
            finish()
        }
        getButton.setOnClickListener {
            getUsers()
        }
        englishButton.setOnClickListener {
            getEnglishUsers()
        }
        japaneseButton.setOnClickListener {
            getJapaneseUsers()
        }
    }

    private fun getUsers() {
        FirebaseFirestore.getInstance().collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val users : List<User> = task.result?.toObjects(User::class.java) ?: listOf()
                    var text : String = ""
                    users.forEach {
                        text += "${it.name}:${it.nativeLanguage}\n"
                    }
                    updateTextView(text)
                }
            }
    }

    private fun getEnglishUsers() { // 条件取得する
        FirebaseFirestore.getInstance().collection("users")
            .whereEqualTo("nativeLanguage", "英語")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val users : List<User> = task.result?.toObjects(User::class.java) ?: listOf()
                    var text : String = ""
                    users.forEach {
                        text += "${it.name}:${it.nativeLanguage}\n"
                    }
                    updateTextView(text)
                }
            }
    }

    private fun getJapaneseUsers() { // 全件取得して、filterする
        FirebaseFirestore.getInstance().collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val users : List<User> = task.result?.toObjects(User::class.java) ?: listOf()
                    var text : String = ""
                    users.filter { it.nativeLanguage == "日本語" }.forEach {
                        text += "${it.name}:${it.nativeLanguage}\n"
                    }
                    updateTextView(text)
                }
            }
    }

    private fun updateTextView(text: String) {
        textView.text = text
    }

    companion object {
        fun start(activity: Activity) =
            activity.startActivity(Intent(activity, UsersActivity::class.java))
    }
}