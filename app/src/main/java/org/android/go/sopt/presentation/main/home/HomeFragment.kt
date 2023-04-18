package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import org.android.go.sopt.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    lateinit var selectionTracker: SelectionTracker<Long>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titleAdapter = HomeTitleAdapter(requireContext())
        val itemAdapter = HomePlayListAdapter()
        setAdapter(titleAdapter, itemAdapter)
        selectionTracker = binding.rcvHomeView.let { recyclerView ->
            SelectionTracker.Builder(
                "mySelection",
                recyclerView,
                HomePlayListAdapter.SelectionKeyProvider(itemAdapter),
                HomePlayListAdapter.SelectionDetailsLookup(recyclerView),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
            ).build()
        }
        setSelectionTracker(selectionTracker, itemAdapter, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::selectionTracker.isInitialized) {
            selectionTracker.onSaveInstanceState(outState)
        }
    }

    private fun setAdapter(titleAdapter: HomeTitleAdapter, itemAdapter: HomePlayListAdapter) {
        itemAdapter.submitList(viewModel.itemList)
        binding.rcvHomeView.also {
            it.adapter = ConcatAdapter(titleAdapter, itemAdapter)
            it.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setSelectionTracker(
        selectionTracker: SelectionTracker<Long>,
        itemAdapter: HomePlayListAdapter,
        savedInstanceState: Bundle?
    ) {
        savedInstanceState.let {
            selectionTracker.onRestoreInstanceState(it)
        }
        itemAdapter.selectionTracker = selectionTracker
        selectionTracker.addObserver(object : SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionChanged() {
                selectionTracker.selection
            }
        })
    }
}
