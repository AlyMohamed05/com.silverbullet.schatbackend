package com.silverbullet.core.data.db.dao

import com.silverbullet.core.data.db.interfaces.RefreshTokenDao
import com.silverbullet.core.data.model.RefreshToken
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class RefreshTokenDaoImpl(db: CoroutineDatabase): RefreshTokenDao {

    private val collection = db.getCollection<RefreshToken>()

    override suspend fun storeRefreshToken(refreshToken: RefreshToken) {
        collection.insertOne(refreshToken)
    }

    override suspend fun getUserRefreshToken(userId: Int): String? {
        return  collection
            .findOne(RefreshToken::userId  eq userId)
            ?.token
    }

    override suspend fun deleteTokenForUser(userId: Int): Boolean {
        return collection
            .deleteOne(RefreshToken::userId eq userId)
            .deletedCount > 0
    }
}