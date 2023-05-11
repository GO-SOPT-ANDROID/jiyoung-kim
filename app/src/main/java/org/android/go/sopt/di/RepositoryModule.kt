package org.android.go.sopt.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.go.sopt.data.repository.AuthRepositoryImpl
import org.android.go.sopt.data.repository.ReqresRepositoryImpl
import org.android.go.sopt.domain.repository.AuthRepository
import org.android.go.sopt.domain.repository.ReqresRepository
import javax.inject.Singleton

/*
@binds : interface타입의 객체를 어떻게 만드는지 Hilt에게 알려주기 위한 용도
 */

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    fun bindReqresRepository(
        reqresRepositoryImpl: ReqresRepositoryImpl
    ): ReqresRepository
}
