package org.android.go.sopt.presentation.main.search

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import coil.load
import dagger.hilt.android.AndroidEntryPoint
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentSearchBinding
import org.android.go.sopt.util.BindingFragment
import org.android.go.sopt.util.ContentUriRequestBody

@AndroidEntryPoint
class SearchFragment : BindingFragment<FragmentSearchBinding>(R.layout.fragment_search) {
    private val viewModel: SearchViewModel by viewModels()
    /*
        private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        binding.ivSearchImage.load(uri)
    }

     */

    // 갤러리에서 여러개 사진 올릴 수 있음
    private val launcher =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxItems = 3)) { imageUriList: List<Uri> ->
            with(binding) {
                when (imageUriList.size) {
                    0 -> {
                        Toast.makeText(requireContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }

                    1 -> {
                        viewModel.setRequestBody(
                            ContentUriRequestBody(
                                requireContext(),
                                imageUriList[0]
                            )
                        )
                        ivGalleryFirst.load(imageUriList[0])
                        viewModel.uploadProfileImage()
                    }

                    2 -> {
                        ivGalleryFirst.load(imageUriList[0])
                        ivGallerySecond.load(imageUriList[1])
                    }

                    3 -> {
                        ivGalleryFirst.load(imageUriList[0])
                        ivGallerySecond.load(imageUriList[1])
                        ivGalleryThird.load(imageUriList[2])
                    }

                    else -> {
                        Toast.makeText(requireContext(), "3개까지의 이미지만 선택해주세요.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

    private val locatePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(requireContext(), "허락 받음", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "허락 못받음", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locatePermissionLauncher.launch("android.permission.ACCESS_COARSE_LOCATION")
        binding.btnSearchButton.setOnClickListener {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
            // 어떤 형식의 이미지를 가져올건지 타입을 명시함
            // image/* -> 이미지 내부의 모든것을 가져옴
//            launcher.launch("image/*")
        }
    }
}
