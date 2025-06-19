package com.issog.beritainapp.utils

import com.issog.beritainapp.ui.home.model.ItemCategory
import com.issog.core.domain.usecase.BeritainCategory
import com.issog.core.R as RCore

object DataGenerator {
    fun generateCategoryData(): List<ItemCategory> =
        listOf(
            ItemCategory(BeritainCategory.BUSINESS.value.uppercase(), RCore.drawable.ic_business),
            ItemCategory(BeritainCategory.ENTERTAINMENT.value.uppercase(), RCore.drawable.ic_entertainment),
            ItemCategory(BeritainCategory.GENERAL.value.uppercase(), RCore.drawable.ic_general),
            ItemCategory(BeritainCategory.HEALTH.value.uppercase(), RCore.drawable.ic_health),
            ItemCategory(BeritainCategory.SCIENCE.value.uppercase(), RCore.drawable.ic_science),
            ItemCategory(BeritainCategory.SPORTS.value.uppercase(), RCore.drawable.ic_sport),
            ItemCategory(BeritainCategory.TECHNOLOGY.value.uppercase(), RCore.drawable.ic_technology),
        )
}
