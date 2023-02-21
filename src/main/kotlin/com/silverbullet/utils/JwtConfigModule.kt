package com.silverbullet.utils

import com.silverbullet.core.security.token.JwtTokenConfig
import io.ktor.server.application.*
import java.util.concurrent.TimeUnit

fun Application.getJwtTokenConfigFromEnv(): JwtTokenConfig{
    val jwtIssuer = environment.config.property("jwt.domain").getString()
    val jwtAudience = environment.config.property("jwt.audience").getString()
    val jwtRealm = environment.config.property("jwt.realm").getString()
    val jwtSecret = environment.config.property("jwt.secret").getString()

    return JwtTokenConfig(
        issuer = jwtIssuer,
        audience = jwtAudience,
        realm = jwtRealm,
        secret = jwtSecret,
        accessTokenExpiresIn = TimeUnit.DAYS.toMillis(1),
        refreshTokenExpiresIn = TimeUnit.DAYS.toMillis(365)
    )
}