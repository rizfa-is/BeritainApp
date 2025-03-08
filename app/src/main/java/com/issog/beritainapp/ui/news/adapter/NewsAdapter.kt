package com.issog.beritainapp.ui.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.issog.beritainapp.databinding.BeritainItemNewsBinding
import com.issog.core.domain.model.ArticleModel

class NewsAdapter: PagingDataAdapter<ArticleModel, NewsViewHolder>(DiffNewsCallback()) {
    private var onClick: (news: ArticleModel?) -> Unit = {}

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(getItem(position), onClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            BeritainItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    fun initClick(action:(news: ArticleModel?) -> Unit) {
        onClick = action
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
    fun bind(news: ArticleModel?, onClick:(news: ArticleModel?) -> Unit) {
        Glide.with(binding.root.context)
            .load(news?.urlToImage)
            .placeholder(com.issog.core.R.drawable.ic_placeholder)
            .into(binding.ivNews)

        binding.tvNewsTitle.text = news?.title
        binding.tvNewsDesc.text = news?.content
        binding.tvNewsAuthor.text = news?.author
        binding.root.setOnClickListener { onClick.invoke(news) }
    }
}