package com.issog.beritainapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.issog.beritainapp.databinding.BeritainItemSourceBinding
import com.issog.core.domain.model.SourceModel

class HomeSourceAdapter:RecyclerView.Adapter<HomeSourceAdapter.ViewHolder>() {
    private var onCLick:(item: SourceModel) -> Unit = {}
    private val categoryList = arrayListOf<SourceModel>()

    inner class ViewHolder(private val binding: BeritainItemSourceBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(source: SourceModel) {
            binding.tvSource.text = source.name
            binding.root.setOnClickListener { onCLick.invoke(source) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            BeritainItemSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = categoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    fun initClick(action: (item: SourceModel) -> Unit) {
        onCLick = action
    }

    fun initData(data: List<SourceModel>) {
        categoryList.clear()
        categoryList.addAll(data)
        notifyDataSetChanged()
    }
}