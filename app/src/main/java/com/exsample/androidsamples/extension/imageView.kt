package com.exsample.androidsamples.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("loadImageUrlWithPicasso")
fun ImageView.loadImageMadeByJane(imageUrl: String?) {
    if (imageUrl == null) {
        setImageBitmap(null)
        return
    }
    Picasso.get().load(imageUrl).into(this)
}