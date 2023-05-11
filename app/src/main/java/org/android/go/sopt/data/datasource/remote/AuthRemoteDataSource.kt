package org.android.go.sopt.data.datasource.remote

import android.util.Log
import org.android.go.sopt.data.model.request.RequestSignInDto
import org.android.go.sopt.data.model.request.RequestSignUpDto
import org.android.go.sopt.data.model.response.ResponseSignInDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto
import org.android.go.sopt.data.service.AuthService
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

/*
서버와 직접 통신하여 data를 관리하는 data source
 */
class AuthRemoteDataSource @Inject constructor(private val authService: AuthService) {
    fun signUp(signUpRequest: RequestSignUpDto) =
        authService.signUp(signUpRequest).enqueue(object : retrofit2.Callback<ResponseSignUpDto> {
            override fun onResponse(
                call: Call<ResponseSignUpDto>,
                response: Response<ResponseSignUpDto>
            ) {
                if (response.isSuccessful) {
                    Log.d("auth", response.body().toString())
                } else {
                    Log.d("auth", "실패..")
                }
            }

            override fun onFailure(call: Call<ResponseSignUpDto>, t: Throwable) {
                t.message?.let { Log.d("auth", "실패..") } ?: " ( X)"
            }
        })

    fun signIn(signInRequest: RequestSignInDto): ResponseSignInDto =
        authService.signIn(signInRequest)
}
