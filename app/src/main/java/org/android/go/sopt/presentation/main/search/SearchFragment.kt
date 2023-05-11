package org.android.go.sopt.presentation.main.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentSearchBinding
import org.android.go.sopt.util.BindingFragment

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val memberAdapter = SearchMemberListAdapter()
        setAdapter(memberAdapter)
    }

    private fun setAdapter(adapter: SearchMemberListAdapter) {
        viewModel.soptMembers.observe(viewLifecycleOwner) {
            binding.rcvSearchView.adapter = adapter
            adapter.submitList(viewModel.soptMembers.value)
        }
    }
}
