package org.android.go.sopt.presentation.main.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.go.sopt.data.service.ImageService
import org.android.go.sopt.util.ContentUriRequestBody
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val service: ImageService) : ViewModel() {
    private val _image = MutableLiveData<ContentUriRequestBody>()
    val image: LiveData<ContentUriRequestBody>
        get() = _image

    fun setRequestBody(requestBody: ContentUriRequestBody) {
        _image.value = requestBody
    }

    fun uploadProfileImage() {
        if (image.value == null) { /* 아직 사진이 등록되지 않았다는 로직 처리 */
            Log.d("searchViewModel", "아직 사진 등록이 안됨 !")
        } else {
            viewModelScope.launch {
                runCatching {
                    service.uploadImage(image.value!!.toFormData()) // 이 부분 data source 넣어주자
                    // fun uploadeImage(image: Multipart.part) {}
                }.onSuccess {
                    Log.d("searchViewModel", "성공 ~~ ")
                }.onFailure {
                    Log.d("searchViewModel", "실패.. ")
                }
            }
        }
    }
}
