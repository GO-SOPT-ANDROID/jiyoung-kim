package org.android.go.sopt.presentation.auth

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.go.sopt.data.model.MyInfo
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
    val specialty: MutableLiveData<String> = MutableLiveData()
    private var myInfo: MyInfo? = null

    val isEnabledSignUpBtn = MediatorLiveData<Boolean>().apply {
        addSourceList(id, pwd, name, specialty) { checkSignUpValid() }
    }

    private val _isSignUpSuccess = MutableLiveData(false)
    val isSignUpSuccess: LiveData<Boolean>
        get() = _isSignUpSuccess

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    val signUpResult: MutableLiveData<ResponseSignUpDto> = MutableLiveData()

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

    private fun checkSignUpValid() =
        id.value?.length in 6..10 && pwd.value?.length in 8..12 && !name.value.isNullOrBlank() && !specialty.value.isNullOrBlank()

    fun saveUserInfo() {
        authRepository.updateUserInfo(getInfo())
    }

    fun signUp(id: String, password: String, name: String, skill: String?) {
        viewModelScope.launch {
            authRepository.signUp(id, password, name, skill)
                .onSuccess {
                    _isSignUpSuccess.value = true
                }.onFailure {
                    _isSignUpSuccess.value = false
                }
        }
        Log.d("signUp", "_isSignUpSuccess :: ${_isSignUpSuccess.value}")
    }
}
