package com.issog.core.utils

import android.view.View


fun Int?.orDefault() = this ?: -1

fun Long?.orDefault() = this ?: 0L

fun Double?.orDefault() = this ?: 0.0

fun Boolean?.orDefault() = this ?: false

fun <T> T?.notNull() = this != null

fun <T> T?.isNull() = this == null

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}