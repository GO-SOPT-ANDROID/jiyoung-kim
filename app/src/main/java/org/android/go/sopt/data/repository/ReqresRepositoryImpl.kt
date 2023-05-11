package org.android.go.sopt.data.repository

import android.util.Log
import org.android.go.sopt.data.datasource.remote.ReqresRemoteDataSource
import org.android.go.sopt.data.model.response.ResponseMemberDto
import org.android.go.sopt.domain.repository.ReqresRepository
import javax.inject.Inject

class ReqresRepositoryImpl @Inject constructor(
    private val reqresRemoteDataSource: ReqresRemoteDataSource
) : ReqresRepository {
    override fun getSoptMembers(): Result<ResponseMemberDto> =
        runCatching {
            reqresRemoteDataSource.getMemberInfo()
        }.onSuccess {
            Log.d("reqres", "멤버 조회 성공!")
        }.onFailure {
            Log.d("reqres", "멤버 조회 실패")
        }
}
