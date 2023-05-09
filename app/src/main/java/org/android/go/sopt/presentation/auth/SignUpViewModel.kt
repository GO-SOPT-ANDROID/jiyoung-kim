package org.android.go.sopt.presentation.auth

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.datasource.remote.ServicePool
import org.android.go.sopt.data.model.MyInfo
import org.android.go.sopt.data.model.request.RequestSignUpDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto
import org.android.go.sopt.domain.repository.AuthRepository
import org.android.go.sopt.util.addSourceList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
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

    private val signUpService = ServicePool.signUpService

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

    fun signUp(id: String, pwd: String, name: String, specialty: String) {
        signUpService.signUp(
            RequestSignUpDto(id, pwd, name, specialty)
        ).enqueue(object : Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>
            ) {
                Log.d("SignUpActivity", response.body().toString())
                if (response.isSuccessful) { // 통신 성공
                    _isSignUpSuccess.value = true
                    signUpResult.value = response.body()
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) { // 통신 실패
                _isSignUpSuccess.value = false
                _errorMessage.value = "네트워크 상태가 좋지 않습니다"
                Log.e("SignUpActivity", t.message.toString(), t)
            }
        })
    }
}
