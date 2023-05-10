package org.android.go.sopt.data.datasource.remote

import org.android.go.sopt.data.model.request.RequestSignInDto
import org.android.go.sopt.data.model.request.RequestSignUpDto
import org.android.go.sopt.data.model.response.ResponseSignInDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto
import org.android.go.sopt.data.service.AuthService
import retrofit2.Call

/*
서버와 직접 통신하여 data를 관리하는 data source
 */
class AuthRemoteDataSource(private val authService: AuthService) {
    fun signUp(signUpRequest: RequestSignUpDto): Call<ResponseSignUpDto> =
        authService.signUp(signUpRequest)

    fun signIn(signInResult: RequestSignInDto): Call<ResponseSignInDto> =
        authService.signIn(signInResult)
}
