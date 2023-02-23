package com.silverbullet.core.events

import com.silverbullet.core.model.Message

class DefaultEventsDispatcher : EventsDispatcher() {

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
}