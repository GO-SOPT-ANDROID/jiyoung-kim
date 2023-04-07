package org.android.go.sopt.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.model.MyInfo

class AuthViewModel : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val pwd: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val specialty: MutableLiveData<String> = MutableLiveData()
    private var isButtonEnable: Boolean = false

    var myInfo: MyInfo? = null

    private val _isSignUpValid = MutableLiveData(false)
    val isSignUpValid: LiveData<Boolean>
        get() = _isSignUpValid

    private val _isSignTextInValid = MutableLiveData(false)
    val isSignTextInValid: LiveData<Boolean>
        get() = _isSignTextInValid

    fun checkIsButtonEnable() {
        if (id.toString().isNotEmpty() && pwd.toString().isNotEmpty() && specialty.toString()
            .isNotEmpty()
        ) {
            isButtonEnable = true
        }
    }

    fun getInfo(): MyInfo {
        return MyInfo(
            id.value.toString(),
            pwd.value.toString(),
            name.value.toString(),
            specialty.value.toString()
        )
    }

    fun isSignUpValid() {
        if (id.value!!.length in 6..10 && pwd.value!!.length in 8..12 && !name.value.isNullOrBlank() && !specialty.value.isNullOrBlank()) {
            _isSignUpValid.value = true
        }
    }

    fun isSignInValid(userId: String, userPwd: String) {
        if (id.value == userId && pwd.value == userPwd) {
            _isSignTextInValid.value = true
        }
    }
}
