package org.android.go.sopt.presentation.auth

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import org.android.go.sopt.data.model.MyInfo
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
        runCatching {
            authRepository.signUp(
                id.value.toString(),
                pwd.value.toString(),
                name.value.toString(),
                skill.value.toString()
            )
        }.onSuccess {
            _isSignUpSuccess.value = true
        }.onFailure {
            _isSignUpSuccess.value = false
        }
    }
}
