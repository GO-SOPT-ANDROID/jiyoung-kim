package org.android.go.sopt.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.go.sopt.data.entity.Member

@Serializable
data class ResponseMemberDto(
    @SerialName("page")
    val page: Int,
    @SerialName("per_page")
    val perPage: Int,
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("data")
    val data: List<Member>,
    @SerialName("support")
    val support: Support
) {
    @Serializable
    data class Support(
        @SerialName("url")
        val url: String,
        @SerialName("text")
        val text: String
    )
}
