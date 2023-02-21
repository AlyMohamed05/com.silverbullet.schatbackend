package com.silverbullet.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.silverbullet.core.security.token.JwtTokenConfig
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {

    val jwtConfig by inject<JwtTokenConfig>()

    authentication {
        jwt {
            val jwtAudience = jwtConfig.audience
            realm = jwtConfig.realm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtConfig.secret))
                    .withAudience(jwtAudience)
                    .withIssuer(this@configureSecurity.environment.config.property("jwt.domain").getString())
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}
