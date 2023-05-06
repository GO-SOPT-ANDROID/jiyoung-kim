package org.android.go.sopt.domain.repository

import org.android.go.sopt.domain.model.MyInfo

interface AuthRepository {
    fun deleteUserInfo()
    fun readUserInfo(): MyInfo?
    fun updateUserInfo(userInfo: MyInfo)
    fun setAutoLogin(isAutoLogin: Boolean)
    fun getAutoLogin(): Boolean
}
