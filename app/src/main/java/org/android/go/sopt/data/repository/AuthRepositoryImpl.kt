package org.android.go.sopt.data.repository

import android.util.Log
import org.android.go.sopt.data.datasource.local.AuthDataSource
import org.android.go.sopt.data.model.MyInfo
import org.android.go.sopt.domain.repository.AuthRepository

class AuthRepositoryImpl(private val authDataSource: AuthDataSource) : AuthRepository {
    override fun deleteUserInfo() {
        runCatching {
            authDataSource.clearPreference()
        }.onSuccess {
            Log.d("auth", "회원 탈퇴 성공")
        }.onFailure {
            Log.d("auth", "회원 탈퇴 실패...")
        }
    }

    override fun readUserInfo(): MyInfo? = authDataSource.getUserInfo()

    override fun updateUserInfo(userInfo: MyInfo) {
        runCatching {
            authDataSource.setUserInfo(userInfo)
        }.onSuccess {
            Log.d("auth", "회원 update 성공")
        }.onFailure {
            Log.d("auth", "회원 update 실패...")
        }
    }

    override fun setAutoLogin(isAutoLogin: Boolean) {
        runCatching {
            authDataSource.isAlreadySignUp = isAutoLogin
        }.onSuccess {
            Log.d("auth", "자동로그인 성공")
        }.onFailure {
            Log.d("auth", "자동로그인 실패...")
        }
    }

    override fun getAutoLogin(): Boolean = authDataSource.isAlreadySignUp
}
