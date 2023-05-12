package org.android.go.sopt.domain.repository

import org.android.go.sopt.data.model.MyInfo

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

    fun signUp(
        id: String,
        password: String,
        name: String,
        skill: String?
    )

    fun signIn(id: String, password: String)
}
