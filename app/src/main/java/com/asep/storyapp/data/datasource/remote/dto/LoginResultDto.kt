package com.asep.storyapp.data.datasource.remote.dto

import com.squareup.moshi.Json

data class LoginResultDto(

    @Json(name = "name")
    val name: String?,

    @Json(name = "userId")
    val userId: String?,

    @Json(name = "token")
    val token: String?
)
