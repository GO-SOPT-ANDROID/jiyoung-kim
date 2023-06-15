package org.android.go.sopt.presentation.auth

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.android.go.sopt.data.entity.MyInfo
import org.android.go.sopt.data.model.request.RequestSignUpDto
import org.android.go.sopt.domain.repository.AuthRepository
import org.android.go.sopt.util.Event
import org.android.go.sopt.util.addSourceList
import org.android.go.sopt.util.state.UiState
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel
@Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _signUpState = MutableLiveData<UiState>()
    val signUpState: LiveData<UiState> get() = _signUpState

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

    private val _isSignUpSuccess = MutableLiveData<Event<Boolean>>()
    val isSignUpSuccess: LiveData<Event<Boolean>>
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

//    fun storeUserInfo() {
//        authRepository.updateUserInfo(getInfo())
//    }

    /*
    Q. 여기서 궁금한 점 !
    회원가입이나 로그인에 대한 분기처리를 할 때 UI State로 각자 상황에 맞는 분기처리를 하는게 좋을까,
    아니면 서버통신이 성공했는지에 대한 여부를 나타내는 _isSignUpSuccess 변수를 통해 observe해서 각 boolean 값에 대해 분기처리 해주는게 좋을까?
   */

    fun signUp() {
        val requestSignUpDto = RequestSignUpDto(
            id = id.value.toString(),
            password = pwd.value.toString(),
            name = name.value.toString(),
            skill = skill.value.toString()
        )
        viewModelScope.launch {
            runCatching {
                _signUpState.value = UiState.Loading
                authRepository.signUp(
                    requestSignUpDto
                )
            }.onSuccess {
                _signUpState.value = UiState.Success
                _isSignUpSuccess.value = Event(true)
                authRepository.updateUserInfo(getInfo())
            }.onFailure {
                _signUpState.value = UiState.Failure(WRONG_NETWORK)
                _isSignUpSuccess.value = Event(false)
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
        const val WRONG_NETWORK = 400
    }
}
