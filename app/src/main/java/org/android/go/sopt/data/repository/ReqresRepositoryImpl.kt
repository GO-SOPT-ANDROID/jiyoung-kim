package org.android.go.sopt.data.repository

import org.android.go.sopt.data.datasource.remote.ReqresRemoteDataSource
import org.android.go.sopt.data.model.response.ResponseMemberDto
import org.android.go.sopt.domain.repository.ReqresRepository
import javax.inject.Inject

class ReqresRepositoryImpl @Inject constructor(
    private val reqresRemoteDataSource: ReqresRemoteDataSource
) : ReqresRepository {
    override suspend fun getSoptMembers(): Result<ResponseMemberDto> =
        runCatching {
            reqresRemoteDataSource.getMemberInfo()
        }
}
