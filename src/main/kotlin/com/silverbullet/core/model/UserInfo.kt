package com.silverbullet.core.model

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo(
    val username: String,
    val name: String
)
