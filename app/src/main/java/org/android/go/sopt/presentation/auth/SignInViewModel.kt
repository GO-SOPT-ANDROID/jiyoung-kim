package org.android.go.sopt.presentation.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.model.MyInfo

class SignInViewModel : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val pwd: MutableLiveData<String> = MutableLiveData()
    private lateinit var myInfo: MyInfo
    private val _isSignInValid = MutableLiveData(false)
    val isSignInValid: LiveData<Boolean>
        get() = _isSignInValid

    private val _isInputEmpty = MutableLiveData(true)
    val isInputEmpty: LiveData<Boolean>
        get() = _isInputEmpty

    fun signInValid(signUpId: String, signUpPwd: String) {
        if (id.value == signUpId && pwd.value == signUpPwd) {
            _isSignInValid.value = true
        }
        Log.d("viewmodel", _isSignInValid.value.toString())
    }

    fun checkInputEmpty() {
        _isInputEmpty.value = !(id.value.isNullOrBlank() || pwd.value.isNullOrBlank())
    }

    fun getSignUpInfo(myInfo: MyInfo) {
        this.myInfo = myInfo
    }
}
