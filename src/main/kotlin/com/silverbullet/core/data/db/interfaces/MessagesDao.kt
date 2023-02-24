package com.silverbullet.core.data.db.interfaces

import com.silverbullet.core.data.db.entity.MessageEntity
import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.model.Message

interface MessagesDao {

    suspend fun getChannelMessages(channelId: Int): DbResult.Success<List<Message>>

    suspend fun sendMessage(message: MessageEntity): DbResult<Unit>

    /**
     * @return DbResult.Success with the updated message if it was found and updated else DbResult.Failure
     */
    suspend fun markMessageAsSeen(
        receiverId: Int,
        messageId: String
    ): DbResult<Message>
}