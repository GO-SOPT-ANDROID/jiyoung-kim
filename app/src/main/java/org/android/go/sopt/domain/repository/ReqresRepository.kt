package org.android.go.sopt.domain.repository

import org.android.go.sopt.data.model.response.ResponseMemberDto

interface ReqresRepository {
    fun getSoptMembers(): Result<ResponseMemberDto>
}
