package com.issog.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourceResponse(
	@SerializedName("sources")
	val sources: List<SourcesItem>? = null,
	@SerializedName("status")
	val status: String? = null
): Parcelable {
	@Parcelize
	data class SourcesItem(
		@SerializedName("country")
		val country: String? = null,
		@SerializedName("name")
		val name: String? = null,
		@SerializedName("description")
		val description: String? = null,
		@SerializedName("language")
		val language: String? = null,
		@SerializedName("id")
		val id: String? = null,
		@SerializedName("category")
		val category: String? = null,
		@SerializedName("url")
		val url: String? = null
	): Parcelable
}
