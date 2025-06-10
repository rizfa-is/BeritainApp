package com.issog.beritain.favorite.favorite

import com.issog.core.domain.model.ArticleModel

interface FavoriteClickCallback {
    fun onNewsClick(articleModel: ArticleModel)
    fun onDeleteFavorite(articleModel: ArticleModel)
}