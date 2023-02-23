package com.silverbullet.feature_channels

import com.silverbullet.core.data.db.interfaces.ChannelsDao
import com.silverbullet.core.data.db.interfaces.UserDao
import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.data.db.entity.MessageEntity
import com.silverbullet.core.model.ChannelInfo
import com.silverbullet.feature_channels.model.SendMessageRequest
import com.silverbullet.utils.AlreadyHaveChannel
import com.silverbullet.utils.NoCommonChannelBetweenUsers
import com.silverbullet.utils.UnexpectedError
import com.silverbullet.utils.UserNotFound

class ChannelsController(
    private val usersDao: UserDao,
    private val channelsDao: ChannelsDao
) {

    /**
     * @param userId the user id who sent the request
     * @param username the username the request sender wants to communicate with
     */
    suspend fun createChannel(
        userId: Int,
        username: String
    ) {
        val targetUser = usersDao.getUser(username) as? DbResult.Success ?: throw UnexpectedError()
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
    }

    suspend fun sendMessage(
        senderId: Int,
        request: SendMessageRequest
    ) {
        val receiverUser =
            (usersDao.getUser(request.receiverUsername) as? DbResult.Success)?.data ?: throw UnexpectedError()
        val commonChannelId =
            channelsDao.getCommonChannel(senderId, receiverUser.id).data ?: throw NoCommonChannelBetweenUsers()
        val messageEntity = MessageEntity(
            senderId = senderId,
            receiverId = receiverUser.id,
            channelId = commonChannelId,
            text = request.text,
            timestamp = System.currentTimeMillis()
        )
        if (channelsDao.sendMessage(messageEntity) !is DbResult.Success) {
            throw UnexpectedError()
        }
    }

    suspend fun getUserChannels(userId: Int): List<ChannelInfo> {
        when (val channelsResult = channelsDao.getUserChannels(userId)) {
            is DbResult.Success -> return channelsResult.data
            is DbResult.Failed -> throw UserNotFound()
        }
    }
}