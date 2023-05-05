package org.android.go.sopt.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyInfo(
    val id: String,
    val pwd: String,
    val name: String,
    val specialty: String
) : Parcelable
