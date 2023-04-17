package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import org.android.go.sopt.R
import org.android.go.sopt.data.model.Music
import org.android.go.sopt.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    private val itemList: List<Music> =
        listOf(
            Music(R.drawable.kitkat, 1, "Paul Blanco", "Star Ceiling"),
            Music(R.drawable.kitkat, 2, "Jay Sean", "Do you love me"),
            Music(R.drawable.kitkat, 3, "pH-1", "ZOMBIES"),
            Music(R.drawable.kitkat, 4, "G-DRAGON", "결국 (Feat. 로제 of BLACKPINK)"),
            Music(R.drawable.kitkat, 5, "쿠기(Coogie)", "Justin Bieber (Feat. 박재범)"),
            Music(R.drawable.kitkat, 6, "Drake", "Do not Disturb"),
            Music(R.drawable.kitkat, 7, "Hoody(후디)", "Baby Oh Baby"),
            Music(R.drawable.kitkat, 8, "ASH ISLAND", "Play (Prod. TOIL)"),
            Music(R.drawable.kitkat, 9, "SZA", "I Hate U"),
            Music(R.drawable.kitkat, 10, "처음 마주쳤을 때처럼", "TOIL, GIST")

        )
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
        // Can be called before onCreateView
        if (::selectionTracker.isInitialized) {
            selectionTracker.onSaveInstanceState(outState)
        }
    }

    private fun setAdapter(titleAdapter: HomeTitleAdapter, itemAdapter: HomePlayListAdapter) {
        itemAdapter.submitList(itemList)
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
                selectionTracker.selection // List of selected ids.
            }
        })
    }
}
