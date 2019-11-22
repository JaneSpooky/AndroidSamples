package com.exsample.androidsamples.firebaseAuth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.firebase_auth_activity.*
import kotlin.random.Random

class FirebaseAuthActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.firebase_auth_activity)
        initialize()
    }

    private fun initialize() {
        initLayout()
    }

    private fun initLayout() {
        initClick()
        updateView()
    }

    private fun initClick() {
        closeImageView.setOnClickListener {
            finish()
        }
        registerButton.setOnClickListener {
            register()
        }
        logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun updateView() {
        var user = FirebaseAuth.getInstance().currentUser
        var isLogin = user != null
        registerButton.visibility = if (isLogin) View.INVISIBLE else View.VISIBLE
        logoutButton.visibility =  if (isLogin) View.VISIBLE else View.INVISIBLE
        user?.also {
            userTextView.text = "mail:${user.email}\nisAnonymous:${user.isAnonymous}"
        } ?: run {
            userTextView.text = ""
        }
    }

    private fun register() {
        var randomNum = Random.nextInt(1000000000)
        FirebaseAuth.getInstance().createUserWithEmailAndPassword("jane-$randomNum@gmail.com", "$randomNum")
            .addOnCompleteListener {
               updateView()
            }
    }

    private fun login() {
        FirebaseAuth.getInstance().signInWithEmailAndPassword("めーる", "パスワード")
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        updateView()
    }

    companion object {
        fun start(activity: Activity) = activity.startActivity(Intent(activity, FirebaseAuthActivity::class.java))
    }
}