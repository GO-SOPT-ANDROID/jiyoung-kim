package org.android.go.sopt.presentation.main.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.databinding.ItemHomeTitleBinding

class HomeTitleAdapter(context: Context) :
    RecyclerView.Adapter<HomeTitleAdapter.HomeTitleViewHolder>() {
    class HomeTitleViewHolder(private val binding: ItemHomeTitleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeTitleViewHolder {
        val binding =
            ItemHomeTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeTitleViewHolder(binding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: HomeTitleViewHolder, position: Int) {}
}
