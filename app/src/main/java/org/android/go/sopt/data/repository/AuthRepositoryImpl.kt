package org.android.go.sopt.data.repository

import android.util.Log
import org.android.go.sopt.data.datasource.local.AuthLocalDataSource
import org.android.go.sopt.data.datasource.remote.AuthRemoteDataSource
import org.android.go.sopt.data.model.MyInfo
import org.android.go.sopt.data.model.request.RequestSignInDto
import org.android.go.sopt.data.model.request.RequestSignUpDto
import org.android.go.sopt.data.model.response.ResponseSignInDto
import org.android.go.sopt.data.model.response.ResponseSignUpDto
import org.android.go.sopt.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override fun deleteUserInfo() {
        runCatching {
            authLocalDataSource.clearPreference()
        }.onSuccess {
            Log.d("auth", "회원 탈퇴 성공")
        }.onFailure {
            Log.d("auth", "회원 탈퇴 실패...")
        }
    }

    override fun readUserInfo(): MyInfo? = authLocalDataSource.getUserInfo()

    override fun updateUserInfo(userInfo: MyInfo) {
        runCatching {
            authLocalDataSource.setUserInfo(userInfo)
        }.onSuccess {
            Log.d("auth", "회원 local data update 성공")
        }.onFailure {
            Log.d("auth", "회원 local data update 실패...")
        }
    }

    override fun setAutoLogin(isAutoLogin: Boolean) {
        runCatching {
            authLocalDataSource.isAlreadySignUp = isAutoLogin
        }.onSuccess {
            Log.d("auth", "자동로그인 성공")
        }.onFailure {
            Log.d("auth", "자동로그인 실패...")
        }
    }

    override fun getAutoLogin(): Boolean = authLocalDataSource.isAlreadySignUp
    override fun signUp(
        id: String,
        password: String,
        name: String,
        skill: String?
    ): Result<ResponseSignUpDto> =
        runCatching {
            authRemoteDataSource.signUp(
                RequestSignUpDto(
                    id,
                    password,
                    name,
                    skill
                )
            )
        }.onSuccess {
            Log.d("auth", "회원가입 성공 !!")
        }.onFailure {
            Log.d("auth", "회원가입 실패..")
        }

    override fun signIn(id: String, password: String): Result<ResponseSignInDto> =
        runCatching {
            authRemoteDataSource.signIn(RequestSignInDto(id, password))
        }.onSuccess {
            Log.d("auth", "로그인 성공 !!")
        }.onFailure {
            Log.d("auth", "로그인 실패..")
        }
}
