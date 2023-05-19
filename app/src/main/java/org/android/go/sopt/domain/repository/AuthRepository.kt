package org.android.go.sopt.domain.repository

import org.android.go.sopt.data.entity.MyInfo
import org.android.go.sopt.data.model.request.RequestSignInDto
import org.android.go.sopt.data.model.request.RequestSignUpDto
import org.android.go.sopt.data.model.response.ResponseSignInDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto

/*
Shared Preference 관련 repository
- 인터페이스 or abstract class 구현체에서는 constructor inject 불가
 */
interface AuthRepository {
    fun deleteUserInfo()
    fun readUserInfo(): MyInfo?
    fun updateUserInfo(userInfo: MyInfo)
    fun setAutoLogin(isAutoLogin: Boolean)
    fun getAutoLogin(): Boolean

    suspend fun signUp(
        requestSignUpDto: RequestSignUpDto
    ): Result<ResponseSignUpDto.SignUpData>

    suspend fun signIn(
        requestSignInDto: RequestSignInDto
    ): Result<ResponseSignInDto.SignInData>
}
