package org.android.go.sopt.presentation.auth

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.go.sopt.data.entity.MyInfo
import org.android.go.sopt.data.model.request.RequestSignUpDto
import org.android.go.sopt.domain.repository.AuthRepository
import org.android.go.sopt.util.addSourceList
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
@Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData("")
    val pwd: MutableLiveData<String> = MutableLiveData("")
    val name: MutableLiveData<String> = MutableLiveData()
    val skill: MutableLiveData<String> = MutableLiveData()
    private var myInfo: MyInfo? = null

    init {
        checkIdValid(id.value.toString())
        checkPwdValid(pwd.value.toString())
    }

    val isIdValid: LiveData<Boolean> = id.map { id -> checkIdValid(id) }
    val isPwdValid: LiveData<Boolean> = pwd.map { pwd -> checkPwdValid(pwd) }

    val isEnabledSignUpBtn = MediatorLiveData<Boolean>().apply {
        addSourceList(id, pwd, name, skill) { checkSignUpValid() }
    }

    private val _isSignUpSuccess = MutableLiveData(false)
    val isSignUpSuccess: LiveData<Boolean>
        get() = _isSignUpSuccess

    private val _errorMessage = MutableLiveData<String>()

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

    private fun checkSignUpValid(): Boolean =
        checkIdValid(id.value.toString()) && checkPwdValid(pwd.value.toString()) && !name.value.isNullOrBlank() && !skill.value.isNullOrBlank()

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

    private fun checkIdValid(id: String): Boolean = id.matches(ID_PATTERN) || id.isEmpty()

    private fun checkPwdValid(pwd: String): Boolean = pwd.matches(PWD_PATTERN) || pwd.isEmpty()

    companion object {
        val ID_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9]{6,10}\$".toRegex()
        val PWD_PATTERN =
            "^(?=.*[a-zA-Z])(?=.*[!@#\$%^&*()])(?=.*[0-9])[a-zA-Z!@#\$%^&*()0-9]{6,12}\$".toRegex()
    }
}
