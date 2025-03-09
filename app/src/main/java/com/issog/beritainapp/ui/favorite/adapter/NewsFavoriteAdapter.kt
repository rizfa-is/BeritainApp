package com.issog.beritainapp.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.issog.beritainapp.databinding.BeritainItemNewsBinding
import com.issog.beritainapp.ui.favorite.FavoriteClickCallback
import com.issog.core.domain.model.ArticleModel
import com.issog.core.utils.ImageUtils.loadImage

class NewsFavoriteAdapter: RecyclerView.Adapter<NewsViewHolder>() {
    private var favoriteCallback: FavoriteClickCallback? = null

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        favoriteCallback?.let { holder.bind(asyncListDiffer.currentList[position], it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            BeritainItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    private val asyncListDiffer = AsyncListDiffer(this, DiffNewsCallback())

    fun saveData( dataResponse: List<ArticleModel>){
        asyncListDiffer.submitList(dataResponse)
    }

    fun initCallback(callback: FavoriteClickCallback) {
        favoriteCallback = callback
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        favoriteCallback = null
    }
}

class DiffNewsCallback: DiffUtil.ItemCallback<ArticleModel>() {
    override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
        return oldItem == newItem
    }
}

class NewsViewHolder(private val binding: BeritainItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(news: ArticleModel, favoriteClickCallback: FavoriteClickCallback) {
        binding.ivNews.loadImage(news.urlToImage)
        binding.ivFavorite.loadImage(com.issog.core.R.drawable.ic_favorite_true)
        binding.tvNewsTitle.text = news.title
        binding.tvNewsDesc.text = news.content
        binding.tvNewsAuthor.text = news.author
        binding.root.setOnClickListener { favoriteClickCallback.onNewsClick(news) }
        binding.ivFavorite.setOnClickListener { favoriteClickCallback.onDeleteFavorite(news) }
    }
}