package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import org.android.go.sopt.R
import org.android.go.sopt.data.model.Music
import org.android.go.sopt.data.model.Title
import org.android.go.sopt.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "앗 ! _binding이 null이다 !" }
    private val itemList: List<Music> =
        listOf(
            Music(R.drawable.kitkat, "Paul Blanco", "Star Ceiling"),
            Music(R.drawable.kitkat, "Jay Sean", "Do you love me"),
            Music(R.drawable.kitkat, "pH-1", "ZOMBIES"),
            Music(R.drawable.kitkat, "G-DRAGON", "결국 (Feat. 로제 of BLACKPINK)"),
            Music(R.drawable.kitkat, "쿠기(Coogie)", "Justin Bieber (Feat. 박재범)"),
            Music(R.drawable.kitkat, "Drake", "Do not Disturb"),
            Music(R.drawable.kitkat, "Hoody(후디)", "Baby Oh Baby"),
            Music(R.drawable.kitkat, "ASH ISLAND", "Play (Prod. TOIL)"),
            Music(R.drawable.kitkat, "SZA", "I Hate U")

        )
    private val title = Title("jiyoung's playlist 🎧")

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
        val titleAdapter = HomeTitleAdapter()
        val itemAdapter = HomePlayListAdapter()
        titleAdapter.submitList(listOf(title))
        itemAdapter.submitList(itemList)
        binding.rcvHomeView.adapter = ConcatAdapter(titleAdapter, itemAdapter)
        binding.rcvHomeView.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
