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

    suspend fun sendEvent(userId: Int, event: Event) {
        val session = subscribedUsers[userId] ?: return
        session.sendSerialized(event)
    }

    abstract suspend fun notifyFriendsWithOnlineStatus(userId: Int, inOnline: Boolean)
}