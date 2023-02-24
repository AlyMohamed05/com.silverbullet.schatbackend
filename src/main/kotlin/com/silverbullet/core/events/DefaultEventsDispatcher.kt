package com.silverbullet.core.events

import com.silverbullet.core.data.db.interfaces.ConnectionsDao
import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.model.Message

class DefaultEventsDispatcher(
    private val connectionsDao: ConnectionsDao
) : EventsDispatcher() {

    override suspend fun onCreateChannel(channelId: Int, vararg channelUsersIds: Int) {
        val event: Event = AddedToChannelEvent(channelId = channelId)
        channelUsersIds.forEach { userId ->
            sendEvent(userId = userId, event = event)
        }
    }

    override suspend fun onReceivedMessage(message: Message, userId: Int) {
        val messageEvent: Event = ReceivedMessage(message = message)
        sendEvent(userId = userId, event = messageEvent)
    }

    override suspend fun onMessageUpdated(senderId: Int, message: Message) {
        val event: Event = UpdatedMessage(message)
        sendEvent(
            userId = senderId,
            event = event
        )
    }

    override suspend fun notifyFriendsWithOnlineStatus(userId: Int, inOnline: Boolean) {
        val event: Event = OnlineStatus(userId = userId, inOnline)
        val userConnections = (connectionsDao.getUserConnectionsIds(userId) as DbResult.Success).data
        userConnections
            .forEach {
                sendEvent(userId = it, event = event)
            }
    }
}