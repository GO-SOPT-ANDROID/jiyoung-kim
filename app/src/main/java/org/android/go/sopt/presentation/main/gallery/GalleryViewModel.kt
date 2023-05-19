package org.android.go.sopt.presentation.main.gallery

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.go.sopt.data.entity.Member
import org.android.go.sopt.domain.repository.ReqresRepository
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(private val reqresRepository: ReqresRepository) :
    ViewModel() {

    private val _soptMembers = MutableLiveData<List<Member>>()
    val soptMembers: LiveData<List<Member>> get() = _soptMembers

    init {
        getSoptMembers()
    }

    private fun getSoptMembers() {
        viewModelScope.launch {
            reqresRepository.getSoptMembers().onSuccess { response ->
                Log.d("reqres", "1단계 성공")
                _soptMembers.value = response.data
            }.onFailure { error ->
                Log.d("reqres", "1단계 실패: ${error.message}")
            }
        }
    }
}
