package org.android.go.sopt.data.datasource.remote

import org.android.go.sopt.data.model.response.ResponseMemberDto
import org.android.go.sopt.data.service.ReqresService
import javax.inject.Inject

class ReqresRemoteDataSource @Inject constructor(private val reqresService: ReqresService) {
    suspend fun getMemberInfo(): ResponseMemberDto = reqresService.getSoptMembers()
}
