package com.silverbullet.core.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

class JwtTokenService(private val tokenConfig: JwtTokenConfig) {

    fun generateAccessToken(vararg claims: TokenClaim): String {
        val expirationDate = Date(System.currentTimeMillis() + tokenConfig.accessTokenExpiresIn)
        return generateJwtTokenWithExpirationDate(
            claims = claims,
            expirationDate = expirationDate
        )
    }

    fun generateRefreshToken(vararg claims: TokenClaim): String {
        val expirationDate = Date(System.currentTimeMillis() + tokenConfig.refreshTokenExpiresIn)
        return generateJwtTokenWithExpirationDate(
            claims = claims,
            expirationDate = expirationDate
        )
    }

    fun getUserIdFromRefreshToken(token: String): Int? {
        return try {
            JWT
                .decode(token)
                .claims["userId"]
                ?.toString()
                ?.replace("\"","") // it returns a string like ""1"" which fails to convert to int
                ?.toIntOrNull()
        }catch (e: Exception){
            null
        }
    }

    private fun generateJwtTokenWithExpirationDate(
        vararg claims: TokenClaim,
        expirationDate: Date
    ): String {
        var token = JWT
            .create()
            .withAudience(tokenConfig.audience)
            .withIssuer(tokenConfig.issuer)
            .withExpiresAt(expirationDate)
        claims.forEach { claim ->
            token = token.withClaim(claim.key, claim.value)
        }
        return token.sign(Algorithm.HMAC256(tokenConfig.secret))
    }

}