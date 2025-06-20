package com.issog.beritainapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.issog.beritainapp.databinding.BeritainItemCategoryBinding
import com.issog.beritainapp.ui.home.model.ItemCategory

class HomeCategoryAdapter : RecyclerView.Adapter<ViewHolder>() {
    private var onCLick: (item: ItemCategory) -> Unit = {}
    private val asyncListDiffer = AsyncListDiffer(this, DiffCategoryCallback())

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(
            BeritainItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(asyncListDiffer.currentList[position], onCLick)
    }

    fun initClick(action: (item: ItemCategory) -> Unit) {
        onCLick = action
    }

    fun initData(data: List<ItemCategory>) {
        asyncListDiffer.submitList(data)
    }
}

class DiffCategoryCallback : DiffUtil.ItemCallback<ItemCategory>() {
    override fun areItemsTheSame(
        oldItem: ItemCategory,
        newItem: ItemCategory,
    ): Boolean {
        return oldItem.category == newItem.category
    }

    override fun areContentsTheSame(
        oldItem: ItemCategory,
        newItem: ItemCategory,
    ): Boolean {
        return oldItem == newItem
    }
}

class ViewHolder(private val binding: BeritainItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        category: ItemCategory,
        onCLick: (item: ItemCategory) -> Unit,
    ) {
        Glide.with(binding.root.context)
            .load(category.image)
            .placeholder(com.issog.core.R.drawable.ic_placeholder)
            .into(binding.ivCategory)
        binding.tvCategory.text = category.category
        binding.root.setOnClickListener { onCLick.invoke(category) }
    }
}
