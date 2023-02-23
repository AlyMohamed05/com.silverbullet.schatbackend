package com.silverbullet.feature_channels

import com.silverbullet.core.data.db.interfaces.ChannelsDao
import com.silverbullet.core.data.db.interfaces.UserDao
import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.events.EventsDispatcher
import com.silverbullet.core.model.ChannelInfo
import com.silverbullet.feature_auth.UserNotFound
import com.silverbullet.utils.UnexpectedError
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ChannelsController(
    private val usersDao: UserDao,
    private val channelsDao: ChannelsDao,
    private val eventsDispatcher: EventsDispatcher
) {

    /**
     * @param userId the user id who sent the request
     * @param username the username the request sender wants to communicate with
     */
    suspend fun createChannel(
        userId: Int,
        username: String
    ) {
        val targetUser = usersDao.getUser(username)
        if (targetUser.data == null)
            throw UserNotFound()
        val targetUserId = targetUser.data.id
        if (channelsDao.alreadyHaveCommonChannel(userId, targetUserId).data)
            throw AlreadyHaveChannel()
        val channelResult = channelsDao.createChannel()
        val channelId = (channelResult as? DbResult.Success)?.data ?: throw UnexpectedError()
        // Now add the users to the channel
        channelsDao.addUserToChannel(userId = userId, channelId = channelId)
        channelsDao.addUserToChannel(userId = targetUserId, channelId = channelId)
        coroutineScope {
            launch {
                eventsDispatcher.onCreateChannel(channelId = channelId, userId, targetUserId)
            }
        }
    }

    suspend fun getUserChannels(userId: Int): List<ChannelInfo> {
        when (val channelsResult = channelsDao.getUserChannels(userId)) {
            is DbResult.Success -> return channelsResult.data
            is DbResult.Failed -> throw UserNotFound()
        }
    }
}