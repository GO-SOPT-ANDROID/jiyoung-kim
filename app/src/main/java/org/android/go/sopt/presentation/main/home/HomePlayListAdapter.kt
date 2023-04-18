package org.android.go.sopt.presentation.main.home

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.data.model.Music
import org.android.go.sopt.databinding.ItemHomePlaylistBinding

class HomePlayListAdapter :
    ListAdapter<Music, HomePlayListAdapter.PlaylistViewHolder>(PlaylistDiffCallback()) {
    lateinit var selectionTracker: SelectionTracker<Long>

    init {
        // RecyclerView에서 long 타입의 유일한 id는 각각 하나의 아이템에만 매칭된다는 의미
        setHasStableIds(true)
    }

    // 선택된 항목에 대한 상세 정보를 제공하는 역할
    // 해당 클래스를 구현하면 선택된 항목의 상세 정보를 추출할 수 있으며
    // 이 정보는 선택 관리자(SelectionTracker)에서 선택된 항목을 처리하는 데 사용
    class SelectionDetailsLookup(private val recyclerView: RecyclerView) :
        ItemDetailsLookup<Long>() {
        override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(event.x, event.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as PlaylistViewHolder).getItemDetails()
            }
            return null
        }
    }

    class SelectionKeyProvider(private val adapter: HomePlayListAdapter) : ItemKeyProvider<Long>(
        SCOPE_CACHED
    ) {
        override fun getKey(position: Int): Long? = adapter.currentList[position].id.toLong()
        override fun getPosition(key: Long): Int =
            adapter.currentList.indexOfFirst { it.id.toLong() == key }
    }

    class PlaylistViewHolder(private val binding: ItemHomePlaylistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Music, selectionTracker: SelectionTracker<Long>) = with(itemView) {
            binding.ivItemImage.setImageDrawable(binding.root.context.getDrawable(data.image))
            binding.tvItemName.text = data.singer
            binding.tvItemUrl.text = data.musicTitle
            bindSelectedState(this, selectionTracker.isSelected(data.id.toLong()))
        }

        private fun bindSelectedState(view: View, selected: Boolean) {
            view.isActivated = selected
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): Long =
                    (bindingAdapter as HomePlayListAdapter).currentList[bindingAdapterPosition].id.toLong()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val binding =
            ItemHomePlaylistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.onBind(
            getItem(position) as Music,
            selectionTracker
        )
    }

    override fun getItemId(position: Int): Long = position.toLong()
}

private class PlaylistDiffCallback : DiffUtil.ItemCallback<Music>() {
    override fun areItemsTheSame(oldItem: Music, newItem: Music): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Music, newItem: Music): Boolean {
        return oldItem == newItem
    }
}
