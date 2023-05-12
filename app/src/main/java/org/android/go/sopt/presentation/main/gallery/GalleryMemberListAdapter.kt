package org.android.go.sopt.presentation.main.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import org.android.go.sopt.data.model.Member
import org.android.go.sopt.databinding.ItemGalleryMemberBinding
import org.android.go.sopt.util.ItemDiffCallback

class GalleryMemberListAdapter :
    ListAdapter<Member, GalleryMemberListAdapter.MemberViewHolder>(
        ItemDiffCallback<Member>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new }
        )
    ) {
    class MemberViewHolder(private val binding: ItemGalleryMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: Member) = with(binding) {
            tvItemMemberName.text = data.firstName
            tvItemMemberEmail.text = data.email
            ivItemMember.load(data.avatar) {
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val binding =
            ItemGalleryMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.onBind(
            getItem(position) as Member
        )
    }
}
