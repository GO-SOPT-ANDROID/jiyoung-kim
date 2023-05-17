package org.android.go.sopt.presentation.main.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.go.sopt.data.entity.MyInfo
import org.android.go.sopt.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
    val userInfo = MutableLiveData<MyInfo>()

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        userInfo.value = authRepository.readUserInfo()
    }

    fun deleteUser() {
        authRepository.deleteUserInfo()
    }
}
