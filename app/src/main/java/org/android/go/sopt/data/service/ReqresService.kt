package org.android.go.sopt.data.service

import org.android.go.sopt.data.model.response.ResponseMemeberDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresService {
    @GET("/api/users")
    fun getSoptMembers(
        @Query("page") num: Int = 2
    ): ResponseMemeberDto
}
