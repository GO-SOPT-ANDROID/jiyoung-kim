package org.android.go.sopt.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.android.go.sopt.data.datasource.local.AuthDataSource
import org.android.go.sopt.data.repository.AuthRepositoryImpl
import org.android.go.sopt.presentation.auth.SignInViewModel
import org.android.go.sopt.presentation.auth.SignUpViewModel
import org.android.go.sopt.presentation.main.mypage.MypageViewModel

class ViewModelFactory(private val applicationContext: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                val repository = AuthRepositoryImpl(AuthDataSource(applicationContext))
                SignUpViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                val repository = AuthRepositoryImpl(AuthDataSource(applicationContext))
                SignInViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MypageViewModel::class.java) -> {
                val repository = AuthRepositoryImpl(AuthDataSource(applicationContext))
                MypageViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}
