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
import com.silverbullet.feature_messages.request.SendMessageRequest
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

    /**
     * Notice that message seen status can only be updated by the user who received this message and not the user;
     * who send the message.
     * The idea is the sender sends the message and waits for the receiver to update the seen status to ensure;
     * that he saw the message.
     */
    suspend fun updateMessageSeenStatus(
        receiverId: Int,
        messageId: String
    ) {
        val status = messagesDao.markMessageAsSeen(receiverId, messageId)
        if (status is DbResult.Failed)
            throw MessageNotFoundException()
        val updatedMessage = (status as DbResult.Success).data
        // Now the sender needs to be notified with the updated message
        eventsDispatcher.onMessageUpdated(
            senderId = updatedMessage.senderId,
            message = updatedMessage
        )
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