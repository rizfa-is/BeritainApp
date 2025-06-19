package com.issog.beritainapp.ui.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.issog.beritainapp.databinding.BeritainPagingLoadingBinding

class NewsLoadingStateAdapter : LoadStateAdapter<NewsLoadingStateAdapter.LoadingStateViewHolder>() {
    private var onClick: () -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoadingStateViewHolder {
        val binding = BeritainPagingLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadingStateViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: LoadingStateViewHolder,
        loadState: LoadState,
    ) {
        holder.bind(loadState)
    }

    inner class LoadingStateViewHolder(private val binding: BeritainPagingLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.retryButton.setOnClickListener { onClick.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.errorMsg.text = loadState.error.localizedMessage
            }
            binding.pbPaging.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState is LoadState.Error
            binding.errorMsg.isVisible = loadState is LoadState.Error
        }
    }

    fun initClick(action: () -> Unit) {
        onClick = action
    }
}
