package org.android.go.sopt.presentation.main.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemGalleryPagerBinding

class GalleryViewPagerAdapter(_itemList: List<Int> = listOf()) :
    RecyclerView.Adapter<GalleryViewPagerAdapter.PagerViewHolder>() {
    lateinit var binding: ItemGalleryPagerBinding
    private var itemList: List<Int> = _itemList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        binding =
            ItemGalleryPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PagerViewHolder(binding)
    }

    class PagerViewHolder(val binding: ItemGalleryPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(src: Int) {
            binding.ivGalleryItem.setImageResource(src)
        }
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }

    fun setItemList(itemList: List<Int>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }
}
