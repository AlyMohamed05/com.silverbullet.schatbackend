package com.silverbullet.core.security.token

data class JwtTokenConfig(
    val issuer: String,
    val audience: String,
    val realm: String,
    val secret: String,
    val accessTokenExpiresIn: Long,
    val refreshTokenExpiresIn: Long
)
