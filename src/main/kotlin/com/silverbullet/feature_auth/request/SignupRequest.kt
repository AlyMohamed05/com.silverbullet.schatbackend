package com.silverbullet.feature_auth.request

import kotlinx.serialization.Serializable

@Serializable
data class SignupRequest(
    val username: String,
    val name: String,
    val password: String
)
