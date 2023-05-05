package org.android.go.sopt.domain.model

import androidx.annotation.DrawableRes

data class Music(
    @DrawableRes val image: Int,
    val id: Int,
    val singer: String,
    val musicTitle: String
)
