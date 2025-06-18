package com.issog.beritainapp.ui.home.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItemCategory(
    val category: String,
    val image: Int,
) : Parcelable
