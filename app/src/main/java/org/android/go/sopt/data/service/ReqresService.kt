package org.android.go.sopt.data.service

import org.android.go.sopt.data.model.response.ResponseMemberDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ReqresService {
    @GET("/api/users")
    suspend fun getSoptMembers(
        @Query("page") num: Int = 2
    ): ResponseMemberDto
}
