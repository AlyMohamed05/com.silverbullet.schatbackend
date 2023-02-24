package com.silverbullet.core.data.db.interfaces

import com.silverbullet.core.data.db.utils.DbResult

interface ConnectionsDao {

    suspend fun connectUser(user1Id: Int, user2Id: Int): DbResult<Unit>

    suspend fun areAlreadyConnected(
        user1Id: Int,
        user2Id: Int
    ): DbResult.Success<Boolean>

    /**
     * @return a list of usernames for users who are connected to this user (friends)
     */
    suspend fun getUserConnectionsIds(userId: Int): DbResult<List<Int>>
}