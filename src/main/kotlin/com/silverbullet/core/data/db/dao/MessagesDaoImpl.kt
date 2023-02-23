package com.silverbullet.core.data.db.dao

import com.silverbullet.core.data.db.entity.MessageEntity
import com.silverbullet.core.data.db.interfaces.MessagesDao
import com.silverbullet.core.data.db.utils.DbResult
import com.silverbullet.core.mappers.toMessage
import com.silverbullet.core.model.Message
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class MessagesDaoImpl(
    db: CoroutineDatabase
) : MessagesDao {

    private val messagesCollection = db.getCollection<MessageEntity>()

    override suspend fun getChannelMessages(channelId: Int): DbResult.Success<List<Message>> {
        val messages = messagesCollection
            .find(MessageEntity::channelId eq channelId)
            .ascendingSort(MessageEntity::timestamp)
            .toList()
            .map(MessageEntity::toMessage)
        return DbResult.Success(messages)
    }
}