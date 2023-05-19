package org.android.go.sopt.data.datasource.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import org.android.go.sopt.data.entity.MyInfo
import javax.inject.Inject

/*
Shared Preference와 같은 local data를 관리하는 data source
 */
class AuthLocalDataSource @Inject constructor(@ApplicationContext applicationContext: Context) {
    private val masterKey = MasterKey
        .Builder(applicationContext, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        applicationContext,
        FILE_NAME,
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    /*
    데이터 기록을 위한 editor
    SharedPreference 파일에 key-vaule값을 저장할 수 있게 해줌
     */
    private val editor: SharedPreferences.Editor = prefs.edit()

    private fun getString(key: String): String {
        return prefs.getString(key, "default").toString()
    }

    private fun setString(key: String, str: String) {
        editor.putString(key, str).apply()
    }

    var isAlreadySignUp: Boolean
        set(value) = prefs.edit { putBoolean(IS_ALREADY_SIGNUP, value) }
        get() = prefs.getBoolean(IS_ALREADY_SIGNUP, false)

    fun getUserInfo(): MyInfo {
        with(prefs) {
            val id = getString(USER_ID)
            val pwd = getString(USER_PASSWORD)
            val name = getString(USER_NAME)
            val specialty = getString(USER_SPECIALTY)

            return MyInfo(id, pwd, name, specialty)
        }
    }

    fun setUserInfo(info: MyInfo) {
        with(prefs) {
            setString(USER_ID, info.id)
            setString(USER_PASSWORD, info.pwd)
            setString(USER_NAME, info.name)
            setString(USER_SPECIALTY, info.skill)
        }
    }

    fun clearPreference() {
        with(editor) {
            clear()
            commit()
        }
    }

    companion object {
        private const val FILE_NAME = "SOPT"
        private const val IS_ALREADY_SIGNUP = "isAlreadySignUp"
        private const val USER_ID = "userId"
        private const val USER_PASSWORD = "userPassword"
        private const val USER_NAME = "userName"
        private const val USER_SPECIALTY = "userSpecialty"
    }
}
