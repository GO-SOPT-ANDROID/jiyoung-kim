package org.android.go.sopt.presentation.main.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.util.BindingFragment

@AndroidEntryPoint
class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val viewModel: GalleryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.vpGalleryMain.adapter = GalleryViewPagerAdapter().apply {
//            setItemList(listOf(R.drawable.kitkat, R.drawable.kitkat, R.drawable.kitkat))
//        }

        val memberAdapter = GalleryMemberListAdapter()
        setAdapter(memberAdapter)
    }

    private fun setAdapter(listAdapter: GalleryMemberListAdapter) {
        viewModel.soptMembers.observe(viewLifecycleOwner) {
            binding.rcvGalleryView.adapter = listAdapter
            listAdapter.submitList(it)
        }
    }
}
