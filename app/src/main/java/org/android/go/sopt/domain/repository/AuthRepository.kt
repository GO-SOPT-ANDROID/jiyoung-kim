package org.android.go.sopt.domain.repository

import org.android.go.sopt.data.model.MyInfo
import org.android.go.sopt.data.model.response.ResponseSignUpDto

// Shared Preference 관련 repository
interface AuthRepository {
    fun deleteUserInfo()
    fun readUserInfo(): MyInfo?
    fun updateUserInfo(userInfo: MyInfo)
    fun setAutoLogin(isAutoLogin: Boolean)
    fun getAutoLogin(): Boolean

    fun signUp(
        id: String,
        password: String,
        name: String,
        skill: String?
    ): Result<ResponseSignUpDto>

    fun signIn(id: String, password: String)
}
