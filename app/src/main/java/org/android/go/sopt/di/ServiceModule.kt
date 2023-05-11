package org.android.go.sopt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.android.go.sopt.data.service.AuthService
import org.android.go.sopt.data.service.ReqresService
import org.android.go.sopt.di.type.BaseUrlType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideAuthService(@RetrofitModule.Retrofit2(BaseUrlType.AUTH) retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Singleton
    @Provides
    fun provideReqresService(@RetrofitModule.Retrofit2(BaseUrlType.REQRES) retrofit: Retrofit): ReqresService =
        retrofit.create(ReqresService::class.java)
}