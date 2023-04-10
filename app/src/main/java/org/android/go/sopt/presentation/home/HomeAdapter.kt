package org.android.go.sopt.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.data.model.Repo
import org.android.go.sopt.databinding.ItemGithubRepoBinding

class HomeAdapter(context: Context) : RecyclerView.Adapter<RepoViewHolder>() {
    private val inflater by lazy { LayoutInflater.from(context) }
    val itemList: List<Repo> =
        listOf(
            Repo("name1", "repo1"),
            Repo("name2", "repo2"),
            Repo("name3", "repo3"),
            Repo("name4", "repo4"),
            Repo("name5", "repo5"),
            Repo("name6", "repo6")
        )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding: ItemGithubRepoBinding = ItemGithubRepoBinding.inflate(
            inflater,
            parent,
            false
        )
        return RepoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.onBind(itemList[position])
    }
}

class RepoViewHolder(private val binding: ItemGithubRepoBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(data: Repo) {
        binding.tvItemName.text = data.name
        binding.tvItemUrl.text = data.repoName
    }
}
