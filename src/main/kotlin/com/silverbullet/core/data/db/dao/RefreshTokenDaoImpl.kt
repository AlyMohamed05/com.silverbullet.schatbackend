package com.silverbullet.core.data.db.dao

import com.silverbullet.core.data.db.interfaces.RefreshTokenDao
import com.silverbullet.core.data.db.entity.RefreshTokenEntity
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class RefreshTokenDaoImpl(db: CoroutineDatabase): RefreshTokenDao {

    private val collection = db.getCollection<RefreshTokenEntity>()

    override suspend fun storeRefreshToken(refreshToken: RefreshTokenEntity) {
        collection.insertOne(refreshToken)
    }

    override suspend fun getUserRefreshToken(userId: Int): String? {
        return  collection
            .findOne(RefreshTokenEntity::userId  eq userId)
            ?.token
    }

    override suspend fun deleteTokenForUser(userId: Int): Boolean {
        return collection
            .deleteOne(RefreshTokenEntity::userId eq userId)
            .deletedCount > 0
    }
}