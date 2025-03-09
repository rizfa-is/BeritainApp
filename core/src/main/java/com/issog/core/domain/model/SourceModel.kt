package com.issog.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceModel(
    val name: String,
    val id: String,
    val description: String,
    val url: String
): Parcelable
