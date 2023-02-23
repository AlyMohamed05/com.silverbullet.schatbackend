package com.silverbullet.feature_messages

import com.silverbullet.core.data.db.entity.MessageEntity
import com.silverbullet.core.data.db.interfaces.ChannelsDao
import com.silverbullet.core.data.db.interfaces.MessagesDao
import com.silverbullet.core.data.db.interfaces.UserDao
import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.events.EventsDispatcher
import com.silverbullet.core.mappers.toMessage
import com.silverbullet.core.model.Message
import com.silverbullet.feature_auth.UserNotFound
import com.silverbullet.feature_channels.NoCommonChannelBetweenUsers
import com.silverbullet.feature_messages.model.SendMessageRequest
import com.silverbullet.utils.UnexpectedError
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MessagesController(
    private val usersDao: UserDao,
    private val channelsDao: ChannelsDao,
    private val messagesDao: MessagesDao,
    private val eventsDispatcher: EventsDispatcher
) {

    suspend fun getChannelMessages(channelId: Int): List<Message> {
        return messagesDao.getChannelMessages(channelId).data
    }

    suspend fun sendMessage(
        senderId: Int,
        request: SendMessageRequest
    ) {
        val receiverUser =
            (usersDao.getUser(request.receiverUsername) as? DbResult.Success)?.data ?: throw UserNotFound()
        val commonChannelId =
            channelsDao.getCommonChannelId(senderId, receiverUser.id).data ?: throw NoCommonChannelBetweenUsers()
        val messageEntity = MessageEntity(
            senderId = senderId,
            receiverId = receiverUser.id,
            channelId = commonChannelId,
            text = request.text,
            timestamp = System.currentTimeMillis()
        )
        if (messagesDao.sendMessage(messageEntity) !is DbResult.Success) {
            throw UnexpectedError()
        }
        notifyReceivingUser(message = messageEntity.toMessage(), receiverId = receiverUser.id)
    }

    private suspend fun notifyReceivingUser(message: Message, receiverId: Int) {
        coroutineScope {
            launch {
                eventsDispatcher.onReceivedMessage(
                    message = message,
                    userId = receiverId
                )
            }
        }
    }
}