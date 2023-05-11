package org.android.go.sopt.presentation.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import org.android.go.sopt.data.model.Member
import org.android.go.sopt.databinding.ItemSearchMemberBinding
import org.android.go.sopt.util.ItemDiffCallback

class SearchMemberListAdapter :
    ListAdapter<Member, SearchMemberListAdapter.MemberViewHolder>(
        ItemDiffCallback<Member>(
            onContentsTheSame = { old, new -> old == new },
            onItemsTheSame = { old, new -> old == new }
        )
    ) {
    class MemberViewHolder(private val binding: ItemSearchMemberBinding) :
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
            ItemSearchMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.onBind(
            getItem(position) as Member
        )
    }
}
