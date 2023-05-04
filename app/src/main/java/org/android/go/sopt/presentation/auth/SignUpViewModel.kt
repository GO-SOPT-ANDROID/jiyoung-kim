package org.android.go.sopt.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.model.MyInfo

class SignUpViewModel : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val pwd: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val specialty: MutableLiveData<String> = MutableLiveData()
    private var myInfo: MyInfo? = null
    private val _isSignUpValid = MutableLiveData(false)
    val isSignUpValid: LiveData<Boolean>
        get() = _isSignUpValid

    fun getInfo(): MyInfo {
        return MyInfo(
            id.value.toString(),
            pwd.value.toString(),
            name.value.toString(),
            specialty.value.toString()
        ).also {
            myInfo = it
        }
    }

    fun signUpValid() {
        _isSignUpValid.value =
            id.value!!.length in 6..10 && pwd.value!!.length in 8..12 && !name.value.isNullOrBlank() && !specialty.value.isNullOrBlank()
    }
}
