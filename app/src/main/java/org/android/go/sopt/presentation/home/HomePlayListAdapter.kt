package org.android.go.sopt.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.data.model.Music
import org.android.go.sopt.databinding.ItemHomePlaylistBinding

class HomePlayListAdapter :
    ListAdapter<Music, HomePlayListAdapter.PlaylistViewHolder>(PlaylistDiffCallback()) {

    class PlaylistViewHolder(private val binding: ItemHomePlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Music) {
            binding.ivItemImage.setImageDrawable(binding.root.context.getDrawable(data.image))
            binding.tvItemName.text = data.singer
            binding.tvItemUrl.text = data.musicTitle
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding =
            ItemHomePlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
}

private class PlaylistDiffCallback : DiffUtil.ItemCallback<Music>() {
    override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
        return oldItem == newItem
    }
}
