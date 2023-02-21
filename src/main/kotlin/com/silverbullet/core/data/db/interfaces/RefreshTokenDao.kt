package com.silverbullet.core.data.db.interfaces

import com.silverbullet.core.data.model.RefreshToken

interface RefreshTokenDao {

    suspend fun storeRefreshToken(refreshToken: RefreshToken)

    suspend fun getUserRefreshToken(userId: Int): String?

    suspend fun deleteTokenForUser(userId: Int): Boolean
}