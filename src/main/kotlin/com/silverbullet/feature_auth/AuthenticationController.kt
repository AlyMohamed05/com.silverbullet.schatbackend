package com.silverbullet.feature_auth

import com.silverbullet.core.data.db.interfaces.RefreshTokenDao
import com.silverbullet.core.data.db.interfaces.UserDao
import com.silverbullet.core.data.db.utils.DbError
import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.data.db.entity.RefreshTokenEntity
import com.silverbullet.core.model.UserInfo
import com.silverbullet.core.security.hashing.SHA256HashingService
import com.silverbullet.core.security.hashing.SaltedHash
import com.silverbullet.core.security.token.JwtTokenService
import com.silverbullet.core.security.token.TokenClaim
import com.silverbullet.feature_auth.model.LoginRequest
import com.silverbullet.feature_auth.model.SignupRequest
import com.silverbullet.feature_auth.model.Tokens
import com.silverbullet.utils.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class AuthenticationController(
    private val hashingService: SHA256HashingService,
    private val jwtTokenService: JwtTokenService,
    private val userDao: UserDao,
    private val refreshTokenDao: RefreshTokenDao
) {

    suspend fun signup(request: SignupRequest): UserInfo {
        val hashedPassword = hashingService.generateSaltedHash(request.password)
        val result = userDao.createUser(
            username = request.username,
            name = request.name,
            password = hashedPassword.hash,
            salt = hashedPassword.salt
        )
        if (result is DbResult.Failed) {
            if (result.error == DbError.DUPLICATE_KEY) {
                throw UsernameAlreadyExists()
            }
        }
        return (result as DbResult.Success).data
    }

    suspend fun login(request: LoginRequest): Tokens {
        val result = userDao.getUser(username = request.username) as? DbResult.Success ?: throw UnexpectedError()
        // I'm not checking for DbFailed here, yet
        // It shouldn't fail it should just return null when user it not found.
        val user = result.data ?: throw UserNotFound()
        val isValidPassword = hashingService.verify(
            value = request.password,
            saltedHash = SaltedHash(hash = user.password, salt = user.salt)
        )
        if (!isValidPassword) {
            throw InvalidCredentials()
        }
        val userClaim = TokenClaim(key = "userId", value = user.id.toString())
        val accessToken = jwtTokenService.generateAccessToken(userClaim)
        val refreshToken = jwtTokenService.generateRefreshToken(userClaim)
        coroutineScope {
            launch {
                refreshTokenDao.storeRefreshToken(
                    RefreshTokenEntity(token = refreshToken, userId = user.id)
                )
            }
        }
        return Tokens(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    /**
     * @return a new jwt access token
     * // TODO: This function needs to be modified as for now it's just not secure and you can't change the state of refresh token to be invalid
     */
    suspend fun refreshToken(token: String): String {
        val userId = jwtTokenService
            .getUserIdFromRefreshToken(token) ?: throw InvalidRefreshToken()
        val userToken = refreshTokenDao
            .getUserRefreshToken(userId) ?: throw InvalidRefreshToken()
        if (token == userToken) {
            val userClaim = TokenClaim(key = "userId", value = userId.toString())
            return jwtTokenService.generateAccessToken(userClaim)
            // TODO: You need to add more security by checking if the refresh token is valid or not and so on.
        }
        // it should have returned a new access token and if reach here then token in invalid
        throw InvalidRefreshToken()
    }

    suspend fun logout(userId: Int) {
        // for now just deleting the refresh token to prevent reusing it to access protected routes
        val isDeleted = refreshTokenDao.deleteTokenForUser(userId)
        if (!isDeleted)
            throw UnexpectedError()
    }
}