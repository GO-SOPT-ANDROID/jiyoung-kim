package org.android.go.sopt.data.service

import org.android.go.sopt.data.model.request.RequestSignInDto
import org.android.go.sopt.data.model.request.RequestSignUpDto
import org.android.go.sopt.data.model.response.ResponseSignInDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/sign-up")
    fun signUp(
        @Body body: RequestSignUpDto
    ): ResponseSignUpDto

    @POST("/sign-in")
    fun signIn(
        @Body request: RequestSignInDto
    ): Call<ResponseSignInDto>
}
