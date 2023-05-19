package org.android.go.sopt.presentation.auth

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.go.sopt.data.entity.MyInfo
import org.android.go.sopt.data.model.request.RequestSignInDto
import org.android.go.sopt.data.model.response.ResponseSignInDto
import org.android.go.sopt.domain.repository.AuthRepository
import org.android.go.sopt.util.addSourceList
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val pwd: MutableLiveData<String> = MutableLiveData()
    private lateinit var myInfo: MyInfo
    private val _isSignInValid = MutableLiveData(false)
    val isSignInValid: LiveData<Boolean>
        get() = _isSignInValid
    private val _isAutoSignInValid = MutableLiveData(false)
    val isAutoSignInValid: LiveData<Boolean>
        get() = _isAutoSignInValid

    private val _isSignInSuccess = MutableLiveData(false)
    val isSignInSuccess: LiveData<Boolean>
        get() = _isSignInSuccess

    val isEnabledSignInBtn = MediatorLiveData<Boolean>().apply {
        addSourceList(id, pwd) { checkSignInValid() }
    }

    private val _signInResult: MutableLiveData<ResponseSignInDto> = MutableLiveData()
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    init {
        setAutoLogin()
    }

    fun signInValid(signUpId: String, signUpPwd: String) {
        if (id.value == signUpId && pwd.value == signUpPwd) {
            authRepository.setAutoLogin(true)
            signIn()
            _isSignInValid.value = true
        }
        Log.d("SignInViewmodel", _isSignInValid.value.toString())
    }

    private fun checkSignInValid() =
        !id.value.isNullOrBlank() && !pwd.value.isNullOrBlank()

    private fun setAutoLogin() {
        if (authRepository.getAutoLogin() && authRepository.readUserInfo() != null) {
            _isAutoSignInValid.value = true
        }
        Log.d("auto", _isAutoSignInValid.value.toString())
    }

    fun getSignUpInfo(myInfo: MyInfo) {
        this.myInfo = myInfo
    }

    fun signIn() {
        val requestSignInDto = RequestSignInDto(
            id = id.value.toString(),
            password = pwd.value.toString()
        )
        viewModelScope.launch {
            runCatching {
                authRepository.signIn(requestSignInDto)
            }.onSuccess {
                _isSignInSuccess.value = true
            }.onFailure {
                _isSignInSuccess.value = false
                _errorMessage.value = it.message
            }
        }
    }
}
