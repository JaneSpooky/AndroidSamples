package com.exsample.androidsamples.firebaseStorage

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.exsample.androidsamples.R
import com.exsample.androidsamples.base.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.firebase_storage_activity.*
import java.io.ByteArrayOutputStream

class FirebaseStorageActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.firebase_storage_activity)
        initialize()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != REQUEST_CODE_CHOOSE_IMAGE || resultCode != Activity.RESULT_OK || data == null)
            return
        data.data?.also {
            Picasso.get().load(it).into(imageView)
            Picasso.get().load(it).into(modalImageVIew)
            frameView.visibility = View.VISIBLE

//            MaterialDialog(this).apply {
//                cancelable(true)
//                val dialogView = LayoutInflater.from(this@FirebaseStorageActivity).inflate(R.layout.image_dialog, null, false)
//                Picasso.get().load(it).into(dialogView.findViewById<ImageView>(R.id.imageView))
//                setContentView(dialogView)
//            }.show()
        }
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
        selectImageButton.setOnClickListener {
            selectImage()
        }
        uploadButton.setOnClickListener {
            upload()
        }
        frameView.setOnClickListener {
            frameView.visibility = View.INVISIBLE
        }
    }

    private fun selectImage() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            Toast.makeText(this, "ログインしてから来てください", Toast.LENGTH_SHORT).show()
            return
        }
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            .addCategory(Intent.CATEGORY_OPENABLE)
            .setType("image/jpeg")
        startActivityForResult(intent, REQUEST_CODE_CHOOSE_IMAGE)
    }

    private fun upload() {
        val ref = FirebaseStorage.getInstance().reference.child("${FirebaseAuth.getInstance().currentUser?.uid ?: "noUser"}/${System.currentTimeMillis()}.jpg")
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos)
        val data = baos.toByteArray()
        ref.putBytes(data)
            .addOnFailureListener {
                Toast.makeText(this, "Upload失敗", Toast.LENGTH_SHORT).show()
                bitmap.recycle()
            }
            .addOnSuccessListener {
                Toast.makeText(this, "Upload成功", Toast.LENGTH_SHORT).show()
                bitmap.recycle()
            }
    }

    companion object {
        private const val REQUEST_CODE_CHOOSE_IMAGE = 1000
        fun start(activity: Activity) = activity.startActivity(Intent(activity, FirebaseStorageActivity::class.java))
    }
}