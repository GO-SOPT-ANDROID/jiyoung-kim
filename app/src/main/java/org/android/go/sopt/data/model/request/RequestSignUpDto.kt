package org.android.go.sopt.data.model.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RequestSignUpDto(
    //  이름이 똑같더라도 함께 써주는 것이 좋다.
    @SerialName("id")
    val id: String,
    @SerialName("password")
    val password: String,
    @SerialName("name")
    val name: String,
    @SerialName("skill")
    val skill: String
)
