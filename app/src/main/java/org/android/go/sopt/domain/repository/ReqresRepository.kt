package org.android.go.sopt.domain.repository

import org.android.go.sopt.data.model.response.ResponseMemberDto

interface ReqresRepository {
    suspend fun getSoptMembers(): Result<ResponseMemberDto>
}
