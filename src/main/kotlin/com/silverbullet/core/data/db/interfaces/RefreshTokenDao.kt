package com.silverbullet.core.data.db.interfaces

import com.silverbullet.core.data.db.entity.RefreshTokenEntity

interface RefreshTokenDao {

    suspend fun storeRefreshToken(refreshToken: RefreshTokenEntity)

    suspend fun getUserRefreshToken(userId: Int): String?

    suspend fun deleteTokenForUser(userId: Int): Boolean
}