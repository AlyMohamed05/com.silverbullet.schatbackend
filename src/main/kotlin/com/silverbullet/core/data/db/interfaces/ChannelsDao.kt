package com.silverbullet.core.data.db.interfaces

import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.model.ChannelInfo

interface ChannelsDao {

    suspend fun createChannel(): DbResult<Int>

    suspend fun addUserToChannel(
        userId: Int,
        channelId: Int
    ): DbResult<Unit>

    /**
     * This can't fail it's either have a common channel or not so it should just return success case with status.
     */
    suspend fun alreadyHaveCommonChannel(
        user1Id: Int,
        user2Id: Int
    ): DbResult.Success<Boolean>

    /**
     * @return the channel id the connects these 2 users
     */
    suspend fun getCommonChannelId(
        user1Id: Int,
        user2Id: Int
    ): DbResult.Success<Int?>


    suspend fun getUserChannels(userId: Int): DbResult<List<ChannelInfo>>
}