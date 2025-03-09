package com.issog.beritainapp.utils

import com.issog.core.domain.model.ArticleModel
import com.issog.core.domain.model.SourceModel

object DummyData {
    fun sourceDummyData() = listOf(
        SourceModel("ABC News","abc","",""),
        SourceModel("BBC News","bbc","",""),
        SourceModel("CNBC","cnbc","",""),
        SourceModel("CNN News","cnn","","")
    )

    fun newsDummyData(): List<ArticleModel> {
        return mutableListOf<ArticleModel>().also {
            (0..20).forEach { number ->
                it.add(
                    ArticleModel(
                        "image $number",
                        "content $number",
                        "author $number",
                        "title $number",
                        "url $number",
                        false
                    )
                )
            }
        }
    }
}