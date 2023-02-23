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

    fun subscribeUser(
        userId: Int,
        session: DefaultWebSocketServerSession
    ) {
        subscribedUsers[userId] = session
    }

    fun unSubscribeUser(
        userId: Int
    ) {
        subscribedUsers.remove(userId)
    }

    suspend fun sendEvent(userId: Int, event: Event) {
        val session = subscribedUsers[userId] ?: return
        session.sendSerialized(event)
    }

    fun isUserOnline(userId: Int): Boolean = subscribedUsers[userId] != null
}