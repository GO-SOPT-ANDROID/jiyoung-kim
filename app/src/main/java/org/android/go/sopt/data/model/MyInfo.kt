package org.android.go.sopt.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyInfo(
    val id: String,
    val pwd: String,
    val name: String,
    val skill: String
) : Parcelable
