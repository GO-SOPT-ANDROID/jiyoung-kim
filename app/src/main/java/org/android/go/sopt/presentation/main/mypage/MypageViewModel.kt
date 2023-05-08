package org.android.go.sopt.presentation.main.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.model.MyInfo
import org.android.go.sopt.domain.repository.AuthRepository

class MypageViewModel(private val authRepository: AuthRepository) : ViewModel() {
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
