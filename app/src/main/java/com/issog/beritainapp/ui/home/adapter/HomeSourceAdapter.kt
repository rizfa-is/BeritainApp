package com.issog.beritainapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.issog.beritainapp.databinding.BeritainItemSourceBinding
import com.issog.core.domain.model.SourceModel

class HomeSourceAdapter : RecyclerView.Adapter<HomeSourceViewHolder>() {
    private var onCLick: (item: SourceModel) -> Unit = {}
    private val asyncListDiffer = AsyncListDiffer(this, DiffSourceCallback())

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeSourceViewHolder {
        return HomeSourceViewHolder(
            BeritainItemSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(
        holder: HomeSourceViewHolder,
        position: Int,
    ) {
        holder.bind(asyncListDiffer.currentList[position], onCLick)
    }

    fun initClick(action: (item: SourceModel) -> Unit) {
        onCLick = action
    }

    fun initData(data: List<SourceModel>) {
        asyncListDiffer.submitList(data)
    }
}

class DiffSourceCallback : DiffUtil.ItemCallback<SourceModel>() {
    override fun areItemsTheSame(
        oldItem: SourceModel,
        newItem: SourceModel,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: SourceModel,
        newItem: SourceModel,
    ): Boolean {
        return oldItem == newItem
    }
}

class HomeSourceViewHolder(private val binding: BeritainItemSourceBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        source: SourceModel,
        onCLick: (item: SourceModel) -> Unit,
    ) {
        binding.tvSource.text = source.name
        binding.root.setOnClickListener { onCLick.invoke(source) }
    }
}
