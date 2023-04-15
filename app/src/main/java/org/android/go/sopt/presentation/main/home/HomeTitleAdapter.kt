package org.android.go.sopt.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.data.model.Title
import org.android.go.sopt.databinding.ItemHomeTitleBinding

class HomeTitleAdapter :
    ListAdapter<Title, HomeTitleAdapter.HomeTitleViewHolder>(TitleDiffCallback()) {

    class HomeTitleViewHolder(private val binding: ItemHomeTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Title) {
            binding.tvHomeTitle.text = data.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeTitleViewHolder {
        val binding =
            ItemHomeTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeTitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeTitleViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

class TitleDiffCallback : DiffUtil.ItemCallback<Title>() {
    override fun areItemsTheSame(oldItem: Title, newItem: Title): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Title, newItem: Title): Boolean {
        return oldItem == newItem
    }
}
