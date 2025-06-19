package com.issog.beritainapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.issog.beritainapp.databinding.BeritainItemCategoryBinding
import com.issog.beritainapp.ui.home.model.ItemCategory

class HomeCategoryAdapter : RecyclerView.Adapter<HomeCategoryAdapter.ViewHolder>() {
    private var onCLick: (item: ItemCategory) -> Unit = {}
    private val categoryList = arrayListOf<ItemCategory>()

    inner class ViewHolder(private val binding: BeritainItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: ItemCategory) {
            Glide.with(binding.root.context)
                .load(category.image)
                .placeholder(com.issog.core.R.drawable.ic_placeholder)
                .into(binding.ivCategory)
            binding.tvCategory.text = category.category
            binding.root.setOnClickListener { onCLick.invoke(category) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return ViewHolder(
            BeritainItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        )
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        holder.bind(categoryList[position])
    }

    fun initClick(action: (item: ItemCategory) -> Unit) {
        onCLick = action
    }

    fun initData(data: List<ItemCategory>) {
        categoryList.clear()
        categoryList.addAll(data)
        notifyDataSetChanged()
    }
}
