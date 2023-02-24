package com.silverbullet.feature_channels

import com.silverbullet.core.data.db.interfaces.ChannelsDao
import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.model.ChannelInfo
import com.silverbullet.feature_auth.UserNotFound

class ChannelsController(
    private val channelsDao: ChannelsDao
) {

    suspend fun getUserChannels(userId: Int): List<ChannelInfo> {
        when (val channelsResult = channelsDao.getUserChannels(userId)) {
            is DbResult.Success -> return channelsResult.data
            is DbResult.Failed -> throw UserNotFound()
        }
    }
}