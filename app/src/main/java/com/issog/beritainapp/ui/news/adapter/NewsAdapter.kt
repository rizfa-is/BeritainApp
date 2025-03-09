package com.issog.beritainapp.ui.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.issog.beritainapp.databinding.BeritainItemNewsBinding
import com.issog.beritainapp.ui.news.NewsItemClickCallback
import com.issog.core.domain.model.ArticleModel
import com.issog.core.utils.ImageUtils.loadImage
import com.issog.core.utils.orDefault

class NewsAdapter: PagingDataAdapter<ArticleModel, NewsViewHolder>(DiffNewsCallback()) {
    private var newsItemClickCallback: NewsItemClickCallback? = null

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position), newsItemClickCallback)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            BeritainItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun initCallback(callback: NewsItemClickCallback) {
        newsItemClickCallback = callback
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        newsItemClickCallback = null
    }
}

class DiffNewsCallback: DiffUtil.ItemCallback<ArticleModel>() {
    override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
        return oldItem == newItem
    }
}

class NewsViewHolder(private val binding: BeritainItemNewsBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(news: ArticleModel?, newsItemClickCallback: NewsItemClickCallback?) {
        var isFavorite = news?.favorite.orDefault()
        val imageFavorite = if (isFavorite)
            com.issog.core.R.drawable.ic_favorite_true
        else
            com.issog.core.R.drawable.ic_favorite_false

        binding.ivFavorite.loadImage(imageFavorite)
        binding.ivNews.loadImage(news?.urlToImage)
        binding.tvNewsTitle.text = news?.title
        binding.tvNewsDesc.text = news?.content
        binding.tvNewsAuthor.text = news?.author
        binding.root.setOnClickListener {
            news?.let { newsItemClickCallback?.onNewsClick(it) }
        }
        binding.ivFavorite.setOnClickListener {
            news?.let {
                if (isFavorite) {
                    binding.ivFavorite.loadImage(com.issog.core.R.drawable.ic_favorite_false)
                    newsItemClickCallback?.onDeleteFavorite(it)
                } else {
                    binding.ivFavorite.loadImage(com.issog.core.R.drawable.ic_favorite_true)
                    newsItemClickCallback?.onSaveFavorite(it)
                }
            }
            isFavorite = !isFavorite
        }
    }
}