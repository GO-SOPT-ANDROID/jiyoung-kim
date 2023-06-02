package org.android.go.sopt.presentation.main.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.util.BindingFragment
import org.android.go.sopt.util.LoadingDialog

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
        val dialog = LoadingDialog(requireContext())
        dialog.show()
        viewModel.soptMembers.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                CoroutineScope(Main).launch {
                    delay(9000)
                    dialog.dismiss()
                }
            } else {
                dialog.dismiss()
                binding.rcvGalleryView.adapter = listAdapter
                listAdapter.submitList(it)
            }
        }
    }
}
