package com.silverbullet.core.events

import com.silverbullet.core.model.Message
import io.ktor.server.websocket.*
import java.util.concurrent.ConcurrentHashMap

abstract class EventsDispatcher {

    private val subscribedUsers = ConcurrentHashMap<Int, DefaultWebSocketServerSession>()

    abstract suspend fun onCreateChannel(
        channelId: Int,
        vararg channelUsersIds: Int
    )

    abstract suspend fun onReceivedMessage(
        message: Message,
        userId: Int
    )

    abstract suspend fun onMessageUpdated(
        senderId: Int,
        message: Message
    )

    suspend fun subscribeUser(
        userId: Int,
        session: DefaultWebSocketServerSession
    ) {
        subscribedUsers[userId] = session
        notifyFriendsWithOnlineStatus(userId, inOnline = true)
    }

    suspend fun unSubscribeUser(
        userId: Int
    ) {
        subscribedUsers.remove(userId)
        notifyFriendsWithOnlineStatus(userId, inOnline = false)
    }

    /**
     * Sends an event to user if he is subscribed to events
     */
    suspend fun sendEvent(userId: Int, event: Event) {
        val session = subscribedUsers[userId] ?: return
        session.sendSerialized(event)
    }

    abstract suspend fun notifyFriendsWithOnlineStatus(userId: Int, inOnline: Boolean)
}