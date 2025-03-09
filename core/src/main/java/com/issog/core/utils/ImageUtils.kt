package com.issog.core.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageUtils {
    fun ImageView.loadImage(imageUrl: String?) {
        Glide.with(context)
            .load(imageUrl)
            .placeholder(com.issog.core.R.drawable.ic_placeholder)
            .into(this)
    }

    fun ImageView.loadImage(imageRes: Int) {
        Glide.with(context)
            .load(imageRes)
            .placeholder(com.issog.core.R.drawable.ic_placeholder)
            .into(this)
    }
}