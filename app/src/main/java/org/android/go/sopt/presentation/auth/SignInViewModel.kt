package org.android.go.sopt.presentation.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.domain.model.MyInfo
import org.android.go.sopt.domain.repository.AuthRepository

class SignInViewModel(private val authRepository: AuthRepository) : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val pwd: MutableLiveData<String> = MutableLiveData()
    private lateinit var myInfo: MyInfo
    private val _isSignInValid = MutableLiveData(false)
    val isSignInValid: LiveData<Boolean>
        get() = _isSignInValid
    private val _isAutoSignInValid = MutableLiveData(false)
    val isAutoSignInValid: LiveData<Boolean>
        get() = _isAutoSignInValid

    private val _isInputEmpty = MutableLiveData(true)
    val isInputEmpty: LiveData<Boolean>
        get() = _isInputEmpty

    init {
        setAutoLogin()
    }

    fun signInValid(signUpId: String, signUpPwd: String) {
        if (id.value == signUpId && pwd.value == signUpPwd) {
            authRepository.setAutoLogin(true)
            _isSignInValid.value = true
        }
        Log.d("SignInViewmodel", _isSignInValid.value.toString())
    }

    fun checkInputEmpty() {
        _isInputEmpty.value = !(id.value.isNullOrBlank() || pwd.value.isNullOrBlank())
    }

    private fun setAutoLogin() {
        if (authRepository.getAutoLogin() && authRepository.readUserInfo() != null) {
            _isAutoSignInValid.value = true
        }
        Log.d("auto", _isAutoSignInValid.value.toString())
    }

    fun getSignUpInfo(myInfo: MyInfo) {
        this.myInfo = myInfo
    }
}
