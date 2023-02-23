package com.silverbullet.feature_messages

import com.silverbullet.core.data.db.interfaces.MessagesDao
import com.silverbullet.core.model.Message

class MessagesController(
    private val messagesDao: MessagesDao
) {

    suspend fun getChannelMessages(channelId: Int): List<Message> {
        return messagesDao.getChannelMessages(channelId).data
    }
}