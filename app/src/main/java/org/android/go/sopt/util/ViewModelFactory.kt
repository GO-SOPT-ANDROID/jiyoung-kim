package org.android.go.sopt.util

/*
class ViewModelFactory(private val applicationContext: Context) : ViewModelProvider.Factory {
    private val authService = ServicePool.authService
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
//            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
//                val repository = AuthRepositoryImpl(
//                    AuthLocalDataSource(applicationContext),
//                    AuthRemoteDataSource(authService)
//                )
//                SignUpViewModel(repository) as T
//            }
            modelClass.isAssignableFrom(SignInViewModel::class.java) -> {
                val repository = AuthRepositoryImpl(
                    AuthLocalDataSource(applicationContext),
                    AuthRemoteDataSource(authService)
                )
                SignInViewModel(repository) as T
            }
            modelClass.isAssignableFrom(MypageViewModel::class.java) -> {
                val repository = AuthRepositoryImpl(
                    AuthLocalDataSource(applicationContext),
                    AuthRemoteDataSource(authService)
                )
                MypageViewModel(repository) as T
            }
            else -> {
                throw IllegalArgumentException("Failed to create ViewModel: ${modelClass.name}")
            }
        }
    }
}


 */
