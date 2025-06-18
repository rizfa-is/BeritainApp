package com.issog.core.utils

import android.view.View

fun Boolean?.orDefault() = this ?: false

fun <T> T?.notNull() = this != null

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}