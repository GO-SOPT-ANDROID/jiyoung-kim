package org.android.go.sopt.presentation.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.go.sopt.data.model.MyInfo
import org.android.go.sopt.domain.repository.AuthRepository
import org.android.go.sopt.util.addSourceList
import javax.inject.Inject
@HiltViewModel
class SignInViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val pwd: MutableLiveData<String> = MutableLiveData()
    private lateinit var myInfo: MyInfo
    private val _isSignInValid = MutableLiveData(false)
    val isSignInValid: LiveData<Boolean>
        get() = _isSignInValid
    private val _isAutoSignInValid = MutableLiveData(false)
    val isAutoSignInValid: LiveData<Boolean>
        get() = _isAutoSignInValid

    val isEnabledSignInBtn = MediatorLiveData<Boolean>().apply {
        addSourceList(id, pwd) { checkSignInValid() }
    }

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
}
