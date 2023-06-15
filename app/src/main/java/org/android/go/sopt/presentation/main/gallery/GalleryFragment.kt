package org.android.go.sopt.presentation.main.gallery

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentGalleryBinding
import org.android.go.sopt.util.LoadingDialog
import org.android.go.sopt.util.base.BindingFragment

@AndroidEntryPoint
class GalleryFragment : BindingFragment<FragmentGalleryBinding>(R.layout.fragment_gallery) {
    private val loadingDialog: LoadingDialog by lazy { LoadingDialog() }
    private val viewModel: GalleryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*
        viewpager 세미나 실습 코드
        binding.vpGalleryMain.adapter = GalleryViewPagerAdapter().apply {
        setItemList(listOf(R.drawable.kitkat, R.drawable.kitkat, R.drawable.kitkat))
            }

         */

        val memberAdapter = GalleryMemberListAdapter()
        setAdapter(memberAdapter)
    }

    private fun setAdapter(listAdapter: GalleryMemberListAdapter) {
        loadingDialog.show(childFragmentManager, "LOADING_DIALOG")
        viewModel.soptMembers.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(1000)
                    loadingDialog.dismiss()
                }
            } else {
                loadingDialog.dismiss()
                binding.rcvGalleryView.adapter = listAdapter
                listAdapter.submitList(it)
            }
        }
    }
}
