package com.issog.core.utils

import android.view.View

fun <T> T?.notNull() = this != null

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}