package org.android.go.sopt.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseSignUpDto(
    @SerialName("status")
    val status: Int,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: SignUpData
) {
    @Serializable
    data class SignUpData(
        @SerialName("name")
        val name: String,
        @SerialName("skill")
        val skill: String
    )
}
