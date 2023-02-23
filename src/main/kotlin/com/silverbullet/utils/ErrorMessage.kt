package com.silverbullet.utils

import kotlinx.serialization.Serializable

@Serializable
data class ErrorMessage(
    val error: String?,
    val errorCode: Int
)
