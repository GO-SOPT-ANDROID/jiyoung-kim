package org.android.go.sopt.presentation.auth

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.go.sopt.data.entity.MyInfo
import org.android.go.sopt.data.model.request.RequestSignUpDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto
import org.android.go.sopt.domain.repository.AuthRepository
import org.android.go.sopt.util.addSourceList
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
@Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val pwd: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val skill: MutableLiveData<String> = MutableLiveData()
    private var myInfo: MyInfo? = null

    val isEnabledSignUpBtn = MediatorLiveData<Boolean>().apply {
        addSourceList(id, pwd, name, skill) { checkSignUpValid() }
    }

    private val _isSignUpSuccess = MutableLiveData(false)
    val isSignUpSuccess: LiveData<Boolean>
        get() = _isSignUpSuccess

    private val _signUpResult: MutableLiveData<ResponseSignUpDto> = MutableLiveData()
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getInfo(): MyInfo {
        return MyInfo(
            id.value.toString(),
            pwd.value.toString(),
            name.value.toString(),
            skill.value.toString()
        ).also {
            myInfo = it
        }
    }

    private fun checkSignUpValid() =
        id.value?.length in 6..10 && pwd.value?.length in 8..12 && !name.value.isNullOrBlank() && !skill.value.isNullOrBlank()

    fun saveUserInfo() {
        authRepository.updateUserInfo(getInfo())
        signUp()
    }

    private fun signUp() {
        val requestSignUpDto = RequestSignUpDto(
            id = id.value.toString(),
            password = pwd.value.toString(),
            name = name.value.toString(),
            skill = skill.value.toString()
        )
        viewModelScope.launch {
            runCatching {
                authRepository.signUp(
                    requestSignUpDto
                )
            }.onSuccess {
                _isSignUpSuccess.value = true
            }.onFailure {
                _isSignUpSuccess.value = false
                _errorMessage.value = it.message
            }
        }
    }
}
