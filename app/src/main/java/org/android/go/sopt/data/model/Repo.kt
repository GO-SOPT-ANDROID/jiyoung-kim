package org.android.go.sopt.data.model

import androidx.annotation.DrawableRes

data class Repo(
    @DrawableRes val image: Int,
    val name: String,
    val repoName: String
)
