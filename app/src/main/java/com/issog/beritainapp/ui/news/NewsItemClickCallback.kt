package com.issog.beritainapp.ui.news

import com.issog.core.domain.model.ArticleModel

interface NewsItemClickCallback {
    fun onNewsClick(articleModel: ArticleModel)

    fun onSaveFavorite(articleModel: ArticleModel)

    fun onDeleteFavorite(articleModel: ArticleModel)
}
