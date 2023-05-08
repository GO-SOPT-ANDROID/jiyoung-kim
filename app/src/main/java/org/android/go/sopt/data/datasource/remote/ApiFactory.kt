package org.android.go.sopt.data.datasource.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.android.go.sopt.BuildConfig
import org.android.go.sopt.data.service.SignUpService
import retrofit2.Retrofit

object ApiFactory {
    private const val AUTH_BASE_URL = BuildConfig.AUTH_BASE_URL
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(AUTH_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
}

object ServicePool {
    val signUpService = ApiFactory.create<SignUpService>()
}
