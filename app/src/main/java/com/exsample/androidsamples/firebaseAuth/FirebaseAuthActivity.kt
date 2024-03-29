package com.exsample.androidsamples.firebaseAuth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.firebase_auth_activity.*
import timber.log.Timber
import kotlin.random.Random

class FirebaseAuthActivity: BaseActivity() {

    private var loginRegisterType = LoginRegisterType.LOGIN

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
        loginTextView.setOnClickListener {
            changeMenu(LoginRegisterType.LOGIN)
        }
        registerTextView.setOnClickListener {
            changeMenu(LoginRegisterType.REGISTER)
        }
        forgetPasswordTextView.setOnClickListener {
            changeMenu(LoginRegisterType.FORGET_MAIL)
        }
        executeButton.setOnClickListener {
            execute()
        }
        logoutButton.setOnClickListener {
            logout()
        }
        changePasswordButton.setOnClickListener {
            changePassword()
        }
    }

    private fun updateView() {
        var user = FirebaseAuth.getInstance().currentUser
        var isLogin = user != null
        loginRegisterView.visibility = if (isLogin) View.INVISIBLE else View.VISIBLE
        memberView.visibility =  if (isLogin) View.VISIBLE else View.INVISIBLE
        user?.also {
            myMailAddressTextView.text = "${user.email}"
            uidTextView.text = "${user.uid}"
        } ?: run {
            myMailAddressTextView.text = ""
            uidTextView.text = ""
        }
        updateLoginRegisterMenuView()
    }

    private fun updateLoginRegisterMenuView() {
        updateMenuTextView(loginTextView, loginRegisterType == LoginRegisterType.LOGIN)
        updateMenuTextView(registerTextView, loginRegisterType == LoginRegisterType.REGISTER)
        updateMenuTextView(forgetPasswordTextView, loginRegisterType == LoginRegisterType.FORGET_MAIL)
        passwordView.visibility = if (loginRegisterType == LoginRegisterType.FORGET_MAIL) View.INVISIBLE else View.VISIBLE
        nameView.visibility = if (loginRegisterType == LoginRegisterType.REGISTER) View.VISIBLE else View.INVISIBLE
    }

    private fun updateMenuTextView(textView: TextView, isSelected: Boolean) {
        textView.apply {
            setTextColor(ContextCompat.getColor(this@FirebaseAuthActivity, if (isSelected) android.R.color.white else android.R.color.black))
            setBackgroundResource(if (isSelected) R.drawable.selector_button_black_to_black_80 else R.drawable.selector_button_white_to_white_80)
        }
    }

    private fun changeMenu(newLoginRegisterType: LoginRegisterType) {
        loginRegisterType = newLoginRegisterType
        updateLoginRegisterMenuView()
    }

    private fun execute() {
        when(loginRegisterType) {
            LoginRegisterType.LOGIN -> login()
            LoginRegisterType.REGISTER -> register()
            LoginRegisterType.FORGET_MAIL -> forgetPassword()
        }
    }

    private fun login() {
        val pair = getPair()
        pair?.also {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(it.first, it.second)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) { // 成功したら

                    } else { // 失敗

                    }
                }
        }
    }

    private fun register() {
        val triple = getTriple()
        triple?.also { triple ->
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(triple.first, triple.second)
                .addOnCompleteListener {
                    Timber.d("register task:$it")
                    toast(if (it.isSuccessful) R.string.firebase_auth_success else R.string.firebase_auth_error)
                    updateView()
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    if (it.isSuccessful && currentUser != null) {
                        val user = User().apply {
                            name = triple.third
                            uid = currentUser.uid
                        }
                        FirebaseFirestore.getInstance().collection("users")
                            // .add(user)
                            .document(currentUser.uid).set(user)
                            .addOnCompleteListener { task ->
                                toast(if (task.isSuccessful) R.string.firebase_auth_success else R.string.firebase_auth_error)
                            }
                    }
                }
        }
    }

    private fun forgetPassword() {
        val pair = getPair(true)
        pair?.also {
            FirebaseAuth.getInstance().sendPasswordResetEmail(pair.first)
                .addOnCompleteListener {
                    Timber.d("forgetPassword task:$it")
                    toast(if (it.isSuccessful) R.string.firebase_auth_success else R.string.firebase_auth_error)
                }
        }
    }

    private fun getPair(isOnlyMailAddress: Boolean = false): Pair<String, String>? {
        val mailAddress = getMailAddress()
        if (mailAddress.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mailAddress).matches()) {
            toast(R.string.firebase_auth_warn_mail)
            return null
        }
        val password = getPassword()
        if (!isOnlyMailAddress && (password.isEmpty() || password.length < 8)){
            toast(R.string.firebase_auth_warn_password)
            return null
        }
        return Pair(mailAddress, password)
    }

    private fun getTriple(): Triple<String, String, String>? {
        val pair = getPair() ?: return null
        val name = getName()
        if (name.isEmpty() || name.length > 11) {
            toast(R.string.firebase_auth_warn_name)
            return null
        }
        return Triple(pair.first, pair.second, name)
    }

    private fun getMailAddress(): String = mailEditText.text.toString()

    private fun getPassword(): String = passwordEditText.text.toString()

    private fun getName(): String = nameEditText.text.toString()

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        updateView()
    }

    private fun changePassword() {
        MaterialDialog(this).show {
            title(R.string.firebase_auth_input_new_password)
            input(inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or InputType.TYPE_CLASS_TEXT) { _, text ->
                changePassword(text.toString())
            }
            positiveButton(R.string.ok)
            negativeButton(R.string.cancel)
        }
    }

    private fun changePassword(password: String) {
        if (password.isEmpty() || password.length < 8){
            toast(R.string.firebase_auth_warn_password)
            return
        }
        FirebaseAuth.getInstance().currentUser?.also {
            it.updatePassword(password).addOnCompleteListener { task ->
                Timber.d("changePassword task:$task")
                toast(if (task.isSuccessful) R.string.firebase_auth_success else R.string.firebase_auth_error)
            }
        } ?: run {
            toast(R.string.firebase_auth_warn_login)
            updateView()
        }
    }

    private fun toast(textId: Int) {
        Toast.makeText(this, textId, Toast.LENGTH_SHORT).show()
    }

    enum class LoginRegisterType {
        LOGIN, REGISTER, FORGET_MAIL
    }

    companion object {
        fun start(activity: Activity) = activity.startActivity(Intent(activity, FirebaseAuthActivity::class.java))
    }
}